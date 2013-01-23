final Form hi = new Form("Hi World");
final WebBrowser b = new WebBrowser(){
  @Override
	public void onLoad(String url) {
		final BrowserComponent c = (BrowserComponent)this.getInternal();
		
		// Create a Javascript context for this BrowserComponent
		final JavascriptContext ctx = new JavascriptContext(c);]
		
		// Create a new Javascript object "camera"
		final JSObject camera = (JSObject)ctx.get("{}");
		
		// Create a capture() method on the camera object
		// as a JSFunction callback.
		camera.set("capture", new JSFunction(){

			public void apply(JSObject self, final Object[] args) {
				Display.getInstance().capturePhoto(new ActionListener(){

					public void actionPerformed(ActionEvent evt) {
						
						String imagePath = (String)evt.getSource();
						
						// Get the callback function that was provided
						// from javascript
						JSObject callback = (JSObject)args[0];
						
						ctx.call(
								callback, // The function
								camera,   // The "this" object
								new Object[]{"file://"+imagePath}  // Parameters
						);
					}
					
				});
			}
			
		});
		
		
		// Add the camera object to the top-level window object
		ctx.set("window.camera", camera);
		
	} 
};

// Load the CameraExample.html file in the browser
b.setURL("jar:///ca/weblite/codename1/tests/CameraExample.html");

hi.setLayout(new BorderLayout());
hi.addComponent(BorderLayout.CENTER, b);

hi.show();