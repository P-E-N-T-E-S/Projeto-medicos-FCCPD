import pika, os

# Access the CLODUAMQP_URL environment variable and parse it (fallback to localhost)
url = "amqps://ojbirxsa:Kj76AqgOMn-rMxISZ8bPnendWfYC6OfU@jackal.rmq.cloudamqp.com/ojbirxsa"
params = pika.URLParameters(url)
connection = pika.BlockingConnection(params)
channel = connection.channel() # start a channel
channel.exchange_declare(exchange='topic-exchange', exchange_type='topic')
channel.queue_declare(queue='atividade01', auto_delete=True) # Declare a queue


while True:
    topic = input("Insira o tópico da mensagem: ")
    msg = input("Insira a sua mensagem: ")
    
    if msg == "sair":
        break
    channel.basic_publish(exchange='topic-exchange',
                      routing_key='atividade01.'+topic,
                      body=msg)

connection.close()