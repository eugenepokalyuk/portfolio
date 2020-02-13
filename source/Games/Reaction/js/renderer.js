
var renderer = {
	init: () => {
		renderer.__textures__['star'].src = consts['star'].src;
		renderer.__textures__['flame'].src = consts['flame'].src;
	},

	render: () => {
		canvas.clear();
		game.__objects__.flames.forEach(flame => {
			canvas.drawImage(
				renderer.__textures__['flame'],
				renderer.__denormalizeRect__({
					x: flame.x,
					y: flame.y,
					w: consts['flame'].width,
					h: consts['flame'].height
				})
			);
		});
		canvas.drawImage(
			renderer.__textures__['star'],
			renderer.__denormalizeRect__({
				x: game.__objects__.star.x,
				y: game.__objects__.star.y,
				w: consts['star'].width,
				h: consts['star'].height
			})
		);
		canvas.drawText(
			renderer.__buildRateSting__(),
			renderer.__buildFont__(),
			renderer.__denormalizePoint__({
				x: consts['rate'].x, 
				y: consts['rate'].y
			})
		);
	},

	__denormalizeRect__: (rect) => ({
		x: rect.x * canvas.width(),
		y: rect.y * canvas.height(),
		w: rect.w * canvas.width(),
		h: rect.h * canvas.height()
	}),

	__denormalizePoint__: (point) => ({
		x: point.x * canvas.width(),
		y: point.y * canvas.height()
	}),

	__buildFont__: () => ({
		color: consts['font'].color,
		name: Math.floor(consts['font'].size * canvas.width()) + 'px ' + consts['font'].name,
	}),

	__buildRateSting__: () => consts['rate'].text + (game.clickCount != 0 ? ((game.successClickCount / game.clickCount) * 100).toPrecision(4) + '%' : '-'),

	__textures__: {
		'star': new Image(),
		'flame': new Image()
	}
}
