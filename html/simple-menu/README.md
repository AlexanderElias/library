## Simple html menu ##

Uses GSAP for animation

### Example of usage###
```html
	<nav id="menu">
		<div id="menu-wrap">
			<div id="menu-inner-wrap">

				<object id="menu-icon" type="image/svg+xml" data="/static/components/menu/menuIcon.svg">&#9776;</object>

				<ul id="menu-list">
					<li class="active">
						<a href="/">Home</a>
					</li>
					<li>
						<a href="/jewelry">Jewelry</a>
					</li>
					<li>
						<a href="/about">About</a>
					</li>
					<li>
						<a href="/gallery">Gallery</a>
					</li>
					<li>
						<a href="/contact">Contact</a>
					</li>
				</ul>

			</div>
		</div>
	</nav>
```

### GSAP dependencies ###
```html
	<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.18.2/TweenLite.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.18.2/TimelineLite.min.js"></script>
```
