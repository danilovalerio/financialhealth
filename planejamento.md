### CRIAR API PARA GASTOS PESSOAIS
## Atividades

1. Criar a estrutura do projeto ok
2. Planejar o sistema
    1. Controle de usuários (CRUD)
       1. Baixar as dependencias (MODEL MAPPER, SPRING SECUTIRY e JWT) ok
       2. Criar model Usuário ok
       3. Criar a autenticação com JWT 
    2. Criar centros de custo (CRUD)
       1. Criar model centro de custo ok 
       2. Todo centro de custo tem que estar vinculado a um usuario
    3. Criar os títulos, APAGAR e ARECEBER (CRUD)
        1. Todo título deve estar vinculado a um Usuário
        2. Todo título deve estar vinculado a 1 ou “N” centro de custo
    4. Criar um endpoint de fluxo de caixa.
        1. Obter o total a pagar, total a receber, o saldo, a lista de títulos a pagar, a lista de títulos a receber podendo filtrar por período de vencimento.