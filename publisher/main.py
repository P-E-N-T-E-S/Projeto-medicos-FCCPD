import pika, os, json
from datetime import datetime
from dotenv import load_dotenv

load_dotenv()
RABBITMQ_URL = os.getenv('RABBITMQ_URL') or os.environ.get('CLOUDAMQP_URL')
EXCHANGE_NAME = "agendamento_exchange"
EXCHANGE_TYPE = "topic"

ESPECIALIDADES = {
    '1': {'name': 'Clinico geral', 'routing_key': 'agendamento.clinico_geral'},
    '2': {'name': 'Cardiologia', 'routing_key': 'agendamento.cardiologia'},
    '3': {'name': 'Pediatria', 'routing_key': 'agendamento.pediatria'}
}

def main():

    try:
        params = pika.URLParameters(RABBITMQ_URL)
        connection = pika.BlockingConnection(params)
        channel = connection.channel()
        channel.exchange_declare(exchange=EXCHANGE_NAME, exchange_type=EXCHANGE_TYPE, durable=True)

        while True:
            print("\n------- Sistema de agendamento médico -------")
            nome = input("Digite o nome do paciente: ")

            print("Escolha uma especialidade:\n1- Clínico geral\n2- Cardiologia\n3- Pediatria")
            especialidade_id = input("Opção (1/2/3): ")

            if especialidade_id not in ['1', '2', '3']:
                print("Opção inválida. Tente novamente.")
                continue

            data_str = input("Digite a data e horário da consulta [dd/mm/yyyy - hh:mm]: ")

            try:
                datetime.strptime(data_str, '%d/%m/%Y - %H:%M')
            except ValueError:
                print("Formato de data inválido! Use dd/mm/yyyy - hh:mm")
                continue


            body = {
                "dataSolicitacao": datetime.now().strftime("%d/%m/%Y - %H:%M"),
                "nome": nome,
                "especialidade": ESPECIALIDADES[especialidade_id]['name'],
                "dataConsulta": data_str
            }

            channel.basic_publish(
                exchange='agendamento_exchange',
                routing_key=ESPECIALIDADES[especialidade_id]['routing_key'],
                body=json.dumps(body)
            )


            print(f" [x] Enviado: {body} para {ESPECIALIDADES[especialidade_id]['routing_key']}")

            continuar = input("Deseja agendar outra consulta? (s/n): ")
            if continuar.lower() == 'n':
                connection.close()
                print("Conexão encerrada")
                break
    except Exception as e:
        print(f"Ocorreu um erro: {str(e)}")


if __name__ == '__main__':
    main()