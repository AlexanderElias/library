(function () {
	'use strict';

	function AseGalleryController ($attrs,$http,$window) {
		var self = this;

		var images;
		var imageWidth = 0;
		var lastImage = 0;
		var activeImage = 0;
		var firstImage = 0;
		var imagesLength = 0;
		var activeImageIndex = 0;

		var aseGalleryPath = $attrs.componentsPath + '/ase-gallery/';
		var imagesPath = $attrs.imagesPath || aseGalleryPath + 'gallery/';

		self.imagesPath = imagesPath;
		self.aseGalleryPath = aseGalleryPath;

		self.isMoreThanOneImage = false;
		self.activeImageIndex = activeImage;
		self.setForwardImage = setForwardImage;
		self.setBackwardImage = setBackwardImage;
		self.scrollContainer = scrollContainer;

		$http.get(imagesPath + 'gallery.json' ).then(
			function (success) {
				images = success.data.images;
				imagesLength = images.length;
				lastImage = imagesLength -1;

				self.images = images;
				self.isMoreThanOneImage = (imagesLength > 1) ? true : false;
			},
			function (error) {
				console.log(error);
			}
		);

		function setForwardImage () {
			activeImage = self.activeImageIndex;

			if (activeImage !== lastImage) {
				imageWidth = document.querySelector('.activeImage').width;
				self.activeImageIndex = activeImage + 1;
				self.galleryContainer = { marginLeft: (imageWidth * self.activeImageIndex * -1) + 'px' };
			}
		}

		function setBackwardImage () {
		     activeImage = self.activeImageIndex;

		     if (activeImage !== firstImage) {
			     imageWidth = document.querySelector('.activeImage').width;
			     self.activeImageIndex = activeImage - 1;
			     self.galleryContainer = { marginLeft: (imageWidth * self.activeImageIndex * -1) + 'px' };
		     }
	     }

		function scrollContainer (index) {
			if (index !== self.activeImageIndex) {

				imageWidth = document.querySelector('.activeImage').width;
				self.activeImageIndex = index;
				self.galleryContainer = { marginLeft: (imageWidth * index * -1) + 'px' };
			}
		}
	}

	var directive = function () {
          return {
		    scope: {},
		    controller: ['$attrs','$http','$window', AseGalleryController],
		    controllerAs: 'AGC',
		    bindings: {
			    imagesUrl: '='
		    },
		    templateUrl: function ($element, $attrs) {
			    return $attrs.componentsPath + 'ase-gallery/gallery.html';
		    }
          };
      };

	angular.module('aseGallery', []).directive('aseGallery', directive);

})();
