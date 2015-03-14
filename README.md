# UtterlySwagger

This is an UtterlyIdle module that creates endpoints that provide version 1.2 and 2.0 formatted swagger ([http://swagger.io/]()) json files. These files can be used in the 'swagger eco-system' to produce interactive documentation, discoverability and so forth.

## How to use

By adding your module to your UtterlyIdle application you get swagger for free.

    public class ExampleApplication extends RestApplication {
        public ExampleApplication(BasePath basePath) {
            super(basePath);

            add(new SwaggerModule(new SwaggerInfo("Swagger Application", "0.1")));
            add(new ExampleModule());
        }
    }

The above code snippet will add the UtterlySwagger Swagger module to the `ExampleApplication`. It defaults to serving the swagger files a `/swagger/swagger_v1.2.json` and `/swagger/swagger_v2.json`.

The `SwaggerInfo` object can also define other information such as api-description, api-version and so on, and it is possible to supply a base path to the swagger files.

## How to install

Simply download the latest build from [releases](https://github.com/tiestvilee/utterlyswagger/releases/) - the *-nodeps.jar will do - and include the library in your application's class path.

## Resources

This code was written to conform to the bits I find interesting of [Swagger 2.0](https://github.com/swagger-api/swagger-spec/blob/master/versions/2.0.md) and [Swagger 1.2](https://github.com/swagger-api/swagger-spec/blob/master/versions/1.2.md).

The tests for the version 2.0 code used the Swagger 2.0 PetStore example to validated compatibility with a real implementation.

## Compatibility

This library is intended to be 100% compatible with Swagger for the bits I find interesting. It is easier to define what it doesn't yet implement.

UtterlySwagger does not yet implement

* tags
* security and security definitions
* body parameter types (though it does do cookie..)
* schemas
* definitions

## License

Read LICENSE.

## Authors

Just me so far, [Tiest Vilee](https://github.com/tiestvilee).