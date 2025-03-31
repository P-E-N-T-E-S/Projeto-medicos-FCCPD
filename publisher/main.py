import pika, os, json
from datetime import datetime
from dotenv import load_dotenv


def main():
    load_dotenv()
    rabbitmq_url = os.getenv('RABBITMQ_URL')
    url = os.environ.get('CLOUDAMQP_URL', rabbitmq_url)

    routing_keys = {
        '1': 'agendamento.clinico_geral',
        '2': 'agendamento.cardiologia',
        '3': 'agendamento.pediatria'
    }

    especialidade = {
        '1': 'Clinico geral',
        '2': 'Cardiologia',
        '3': 'Pediatria'
    }

    try:
        params = pika.URLParameters(url)
        connection = pika.BlockingConnection(params)
        channel = connection.channel()
        channel.exchange_declare(exchange='topic-exchange', exchange_type='topic')

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
                "especialidade": especialidade[especialidade_id],
                "dataConsulta": data_str
            }

            channel.basic_publish(
                exchange='topic-exchange',
                routing_key=routing_keys[especialidade_id],
                body=json.dumps(body)
            )

            print(f" [x] Enviado: {body} para {routing_keys[especialidade_id]}")

            continuar = input("Deseja agendar outra consulta? (s/n): ")
            if continuar.lower() == 'n':
                connection.close()
                print("Conexão encerrada")
                break
    except Exception as e:
        print(f"Ocorreu um erro: {str(e)}")


if __name__ == '__main__':
    main()