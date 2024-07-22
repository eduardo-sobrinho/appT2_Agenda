# Agenda
###### Trabalho Final de Dispositivos Móveis
<br><br>
<a href="#"><img src="https://github.com/eduardo-sobrinho/appT2_Agenda/blob/main/app/src/main/res/mipmap-hdpi/ic_laucher_agenda.png" align="left" width="78"></a>  Agenda telefônica virtual que permite adicionar contatos, bem como atualizar e excluir;
<br>
protegida por uma tela de login e senha, e que permite vários usuários cadastrados simultaneamente.

<br><br>

## Características:

   Possui por padrão um tema claro, com possibilidade de ativar o tema escuro.
   <br>
   Existe também a possibilidade de alterar o tamanho da fonte, com 3 tamanhos disponíveis:
####    pequeno
####    médio - tamanho padrão
####    grande

   O cadastro de um contato possui nome, telefone, data de aniversário.
   <br>
   Foram implementadas máscaras para os campos de cadastro e atualização de contatos:
      <ul>
      <li>nome - apenas letras e espaços são permitidos;</li>
      <li>telefone - adição automática de parênteses, espaço e hífen: (12) 3456-7890</li>
      </ul>
   É possível atualizar dados de um contato,
   <br>
   e excluir um contato.

Por questão de praticidade na implementação, tema e fonte são restaurados para o valor padrão após o logoff do usuário, sendo que a aplicação é encerrada após o logoff.

O app usa o banco de dados SQLite e a senha do usuário é salva através do uso de hash, sendo que o hash utilizado foi o BCrypt.

Foi adicionada a possibilidade de excluir a conta de um usuário, onde o mesmo é direcionado para uma tela de confirmação,
sendo que, ao final da exclusão, todos os contatos da conta do usuário também serão excluídos.
<br>
Também foi adicionado um ícone personalizado para o app.


## Tabelas utilizadas

Tabela de usuários  (UsuarioTb)
<br>

| usuarioId | nomeUsuario |                           senhaHash                          |
|----------:|------------:|:------------------------------------------------------------:|
|     1     |  usuário 1  | $2y$12$5xVa9amP//vlltZX.nekiOpMI/8v6QuTRS2xxud0VlgmD.jVIMU5G |
|     2     |  usuário 2  | $2b$10$Go2A6/V5cFfI0QU4f2lhWON7ck2ta6zfbnvUNuhfQGajvfmvFQpDu |
|     3     |  usuário 3  | $2y$12$y4iuMCO9SKIsqfXQWzKLcOEfz1alAJ1q5Jv4qmjIv9UGwMEcru7l6 |

<br>
Tabela de contatos  (ContatoTb)
<br><br>

| contatoId | nomeContato |   telContato   |   anivContato  | usuarioId |
|:---------:|:-----------:|:--------------:|:--------------:|:---------:|
|     1     |  contato 1  | (67) 1234-5678 |   14 de Julho  |     1     |
|     2     |  contato 2  | (21) 9876-5432 |  7 de Setembro |     1     |
|     3     |  contato 3  | (11) 1234-1234 | 25 de Dezembro |     3     |

<br>

Autor: Eduardo Sobrinho
<br>
Ciência da Computação - UFMS

Status do Projeto: Concluído :heavy_check_mark:
