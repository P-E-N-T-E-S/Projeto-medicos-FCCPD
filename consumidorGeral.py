# consume.py
import pika, os

received_message = None

def callback(ch, method, properties, body):
    global received_message  
    received_message = body.decode()  
    print(f" [x] Received: {received_message}")  

topic = input("Insira o tópico que deseja escutar: ")

url = "amqps://ojbirxsa:Kj76AqgOMn-rMxISZ8bPnendWfYC6OfU@jackal.rmq.cloudamqp.com/ojbirxsa"
params = pika.URLParameters(url)
connection = pika.BlockingConnection(params)
channel = connection.channel()  

channel.exchange_declare(exchange='topic-exchange', exchange_type='topic')

channel.queue_declare(queue='atividade01', auto_delete=True)

channel.queue_bind(exchange='topic-exchange', queue='atividade01', routing_key='atividade01.'+topic)

channel.basic_consume(queue='atividade01', on_message_callback=callback, auto_ack=True)

print(' [*] Waiting for messages:')
channel.start_consuming()
