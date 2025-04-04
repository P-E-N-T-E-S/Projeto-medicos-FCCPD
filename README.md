# **Sistema de Agendamento de Consultas Médicas - RabbitMQ**  
---

## **📌 Visão Geral**  
Sistema distribuído onde:  
- **Pacientes (Produtores Python)** enviam solicitações de consulta por especialidade via RabbitMQ (Exchange `topic`).  
- **Assistentes Médicos (Consumidores Java)** processam apenas solicitações de sua especialidade e confirmam/recusam agendamentos.  
- **Backend de Auditoria** registra todas as mensagens (`agendamento.#`) para histórico.  

## **⚙️ Componentes**  

### **1. Produtores (Pacientes) - Python**  
- **Responsabilidade**: Enviar solicitações para:  
  - `agendamento.clinico_geral`  
  - `agendamento.cardiologia`  
  - `agendamento.pediatria`  
- **Formato da Mensagem (JSON)**:  
  ```json
  {
    "dataSolicitacao": "02/04/2025 - 13:46",
    "nome": "paciente2",
    "especialidade": "Cardiologia",
    "dataConsulta": "02/05/2025 - 14:00"
  }
  ```

### **2. Consumidores (Assistentes Médicos) - Java/Spring Boot**  
- **Função**:  
  - Receber solicitações específicas por especialidade.  
  - Verificar disponibilidade no calendário.  
  - Confirmar ou recusar consultas.  
- **Filas por Especialidade**:  
  - Clínico Geral: `agendamento.clinico_geral`  
  - Cardiologia: `agendamento.cardiologia`  
  - Pediatria: `agendamento.pediatria`  

### **3. Backend de Auditoria - Java/Spring Boot**  
- **Função**:  
  - Consumir todas as mensagens (`agendamento.#`).  
  - Registrar histórico completo em banco de dados.  

## **🚀 Como Executar**  

### **Pré-requisitos**  
✅ **Java JDK 11+**  
✅ **Python 3.8+**  
✅ **RabbitMQ**
✅ **Maven** (para o Spring Boot)  

### **Passo 1: Executar Consumidores (Java)**  
1. Entre na pasta do consumidor:  
   ```bash
   cd consumer/
   ```  
2. Execute o Spring Boot:  
   ```bash
   mvnw.cmd spring-boot:run
   ```  
### **Passo 2: Executar Produtor (Python)**  
1. Entre na pasta do produtor:  
   ```bash
   cd publisher/
   ```  
2. Instale as dependências:  
   ```bash
   pip install -r requirements.txt
   ```  
3. Execute o script:  
   ```bash
   python agendamento_paciente.py
   ```  
## **📋 Exemplo de Fluxo**  
1. Paciente envia:  
   ```json
   {'dataSolicitacao': '04/04/2025 - 09:51', 'nome': 'lili', 'especialidade': 'Clinico geral', 'dataConsulta': '04/04/2025 - 14:00'} para agendamento.clinico_geral
   ```  
2. Assistente de Cardiologia:  
   - Verifica disponibilidade.  
   - Confirma consulta (se horário livre).  
3. Auditoria:  
   - Registra a mensagem no histórico.  
