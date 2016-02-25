# Build it bigger

This repository contains the app Buid It Buigger, which is part of the Android Developer Nanodegree program

## Features

This app contains : 

	A Java library that provides jokes
	A Google Cloud Endpoints (GCE) project that serves those jokes
	An Android Library containing an activity for displaying jokes
	An Android app that fetches jokes from the GCE module and passes them to the Android Library for display
	
The app can be built in 2 different flavor
	
	A free flavor that retrieve jokes but display Ad banner and Interstitials Ads.
	A paid flavor that does the same thing but with the Ads.


## Install the app and use your own Google App Engine

Select Run Build > Deploy Module to App Engine and create a project

	Click here to create a new Google Developper Project
	
Note the project_id and paste in src/main/webapp/WEB-INF/appengine-web.xml

    <application>builditbigger-1231</application>

Back to Build > Deploy Module to App Engine, select your project and click 

	Deploy
	
Now change the app/src/main/res/values/strings below line with the url integrating your project_id instead

    <string name="google_app_engine_url">https://builditbigger-1231.appspot.com/_ah/api/</string>

	
## Install the app and use your local App Engine development server instead

in GCEndpointsApiService doInBackground method comment those lines

	JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new
			AndroidJsonFactory(), null)
			.setRootUrl(activity.getResources().getString(R.string.google_app_engine_url));
	myApiService = builder.build();
			
and uncomment those lines

	JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
			new AndroidJsonFactory(), null)
			.setRootUrl("http://10.0.2.2:8080/_ah/api/") //10.0 .2 .2 is localhost 's IP address in Android emulator
			.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
				@Override
				public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
					abstractGoogleClientRequest.setDisableGZipContent(true);
				}
			});

rebuild the project

	build > rebuild
	
select module backend and run it

	Run > Run 'backend'
	
this will start the local appengine server

	task : appengineRun
	
check it on 

	http://localhost:8080

now you can run the app in the emulator. Don't forget to stop server when finished your testing

	Run > Stop


## License
	
		The MIT License (MIT)

	Copyright (c) 2016 ETCHEMENDY ELORRI

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
