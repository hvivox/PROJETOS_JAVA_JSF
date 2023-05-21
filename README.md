<p align="center">
<a href="https://laravel.com" target="_blank">
<img width="200" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" />
</a>
</p>

            

## Sobre

São 3 projetos individuais que utilizam primefaces + CDI + Hibernate JPA

* Formulário: projeto para inscrições eventos culturais e gerenciamento dos inscritos
* SDNI: projeto para controle e publicação de documentos
* PainelMobile: projeto para intragação entre aplicativo mobile e portal institucional

## Comandos para iniciar o projeto

* composer i*nstall
* npm install*
* editar o arquivo .env com os dados corretos
* rodar o comando php artisan key:generate

## Antes de por em produção deve-se rodar o comando para compilar fonts do vuejs

* npm run watch
* npm run build (este comando depende do npm run watch rodando)
* desabilitar APP_DEBUG=true do .env
* Rodar o comando php artisan config:clear sempre ao alterar o env

## Executar a aplicação em desenvolvimento

* php artisan serve
* npm run watch (alguns momentos é necessário sair e entrar nesse comando para recompilar corretamente)

## License

The Laravel framework is open-sourced software licensed under the [MIT license](https://opensource.org/licenses/MIT).
