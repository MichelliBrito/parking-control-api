# parking-control-api

Projeto desenvolvido no vídeo Spring Security | Curso 2022, onde vamos implementar na prática autenticação e controle de acesso com Spring Security em uma aplicação Spring Boot. 

Vamos focar detalhadamente na configuração do Spring Security para implementar Basic Auth utilizando a sua configuração default a princípio, evoluir para uma autenticação em memória, e posteriormente para uma autenticação via base de dados com JPA. Também vamos inserir o controle de acesso via Roles tanto a nível global quanto a nível de métodos nos controllers, utilizando .antMatchers e @PreAuthorize respectivamente. 

E por último, vamos atualizar as configurações do Spring Security, de acordo com a recomendação para as versões a partir de 5.7.0, onde utilizamos agora de componentes ao invés de WebSecurityConfigurerAdapter e seus métodos de substituição.


--->>> Links mostrados no vídeo:

Spring Security sem WebSecurityConfigurerAdapter: https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

CSRF Protection: https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/csrf.html#when-to-use-csrf-protection

Interceptor Postman: https://learning.postman.com/docs/sending-requests/capturing-request-data/interceptor/#installing-interceptor

UUID Generator: https://www.uuidgenerator.net/
