package com.elorri.android.builditbigger.backend;

import com.example.CreateJoke;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        resource = "joke",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.android.elorri.com",
                ownerName = "backend.builditbigger.android.elorri.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());

    /** A simple endpoint method that takes a stringJoke and create an object Joke */
    @ApiMethod(name = "tellaJoke")
    public Joke tellaJoke() {
        Joke aJoke=new Joke();
        aJoke.setJoke((new CreateJoke()).getJoke());
        return aJoke;
    }

}