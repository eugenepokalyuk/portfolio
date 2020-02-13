
var input = {
	init: () => {
		canvas.onmousedown = input.mousedown;
	},

	mousedown: (event) => {
		event = input.__normalize__(event);
		if (input.onmousedown) input.onmousedown(event); 
	},

	__normalize__: (pos) => ({
		x: pos.x * window.devicePixelRatio / canvas.width(),
		y: pos.y * window.devicePixelRatio / canvas.height()
	})
}
