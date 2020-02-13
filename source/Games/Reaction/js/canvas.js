
var canvas = {
	init: () => {
		canvas.elem = document.getElementById("canvas");
		canvas.elem.onmousedown = canvas.mousedown;
		canvas.context = canvas.elem.getContext("2d");
		canvas.resize();
		window.onresize = canvas.resize;
	},

	width: () => canvas.elem.width,
	height: () => canvas.elem.height,

	resize: () => {
		canvas.rect = canvas.elem.getBoundingClientRect();
		canvas.elem.width = canvas.rect.width * window.devicePixelRatio;
		canvas.elem.height = canvas.rect.height * window.devicePixelRatio;
	},

	clear: () => {
		canvas.context.fillStyle = consts['backgroundColor'];
		canvas.context.fillRect(0, 0, canvas.width(), canvas.height());
	},

	drawText: (text, font, point) => {
		canvas.context.fillStyle = font.color;
		canvas.context.font = font.name;
		canvas.context.fillText(text, point.x, point.y);
	},

	drawImage: (img, rect) => {
		canvas.context.drawImage(img, rect.x, rect.y, rect.w, rect.h);
		canvas.context.drawImage(img, rect.x, rect.y, rect.w, rect.h);
	},

	mousedown: (event) => {
		var event = {
			x: event.clientX - canvas.rect.x,
			y: event.clientY - canvas.rect.y
		};
		if (canvas.onmousedown) canvas.onmousedown(event); 
	}
}
