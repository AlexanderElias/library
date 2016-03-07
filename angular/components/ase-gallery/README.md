## Usage Example ##

```html
	<ase-gallery images-path="/static/images/gallery/" components-path="/static/components/"></ase-gallery>
```

### Setup Overview ###
	1. Download and install ase-gallery package
	2. Add the ase-gallery element to your html
	3. Add attributes and paths
	5. Create a directory for the gallery images
	6. Add images to the gallery images directory
	7. Create a *gallery.json* file inside the gallery images directory
	8. Configure *gallery.json* file  
	9. Add 'ase-gallery' to your Angular module dependencies

### components-path ###
	**Required Attribute**
	The `components-path` attribute requires an absolute path to the location of the ase-gallery package.

### images-path ###
	**Not Required Attribute**
	The `images-path` attribute accepts an absolute path to a directory for ase-gallery to access the images and the gallery.json file. The default of as-gallery is the path that was provided for `components-path`.


### Example Of galery.json File ###
````json
	{
		"images": [
			{
				"src": "0.png",
				"alt": "Image 0"
			},
			{
				"src": "1.png",
				"alt": "Image 1"
			}
		]
	}
````
