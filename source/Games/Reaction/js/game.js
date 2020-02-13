
var game = {
	init: () => {
		button.init();
		canvas.init();
		input.init();
		renderer.init();
		canvas.clear();
		input.onmousedown = game.mousedown;
	},

	start: () => {
		button.changeState(button.states['disabled']);
		setTimeout(() => button.changeState(button.states['end']), consts['disableButtonTimeout']);
		game.__loop_var__ = setInterval(game.loop, consts['gameLoopInterval']);
		game.newStar();
	},

	end: () => {
		clearInterval(game.__loop_var__);
		button.changeState(button.states['start']);
		game.successClickCount = 0;
		game.clickCount = 0;
		game.__objects__.flames = [];
		canvas.clear();
	},

	loop: () => {
		renderer.render();
	},

	random: (min, max) => Math.random() * (max - min) + min,

	newStar: () => {
		if (game.__star_var__) clearInterval(game.__star_var__);
		game.generateStar();
		game.__star_var__ = setInterval(
			game.generateStar, 
			game.random(consts['starTimeout'].min, consts['starTimeout'].max)
		);
	},

	pushFlame: (flame) => {
		game.__objects__.flames.push(flame);
		setTimeout(() => game.__objects__.flames.shift(), consts['flameTimeout']);
	},

	generateStar: () => {
		game.__objects__.star = {
			x: game.random(0, 1 - consts['star'].width),
			y: game.random(consts['rate'].y, 1 - consts['star'].height - consts['rate'].y)
		}
	},

	mousedown: (event) => {
		var offset = {
			x: event.x - game.__objects__.star.x,
			y: event.y - game.__objects__.star.y
		};

		game.clickCount++;

		if ((offset.x < consts['star'].width) && (offset.x > 0) && (offset.y < consts['star'].height) && (offset.y > 0))
		{
			game.successClickCount++;
			game.pushFlame(game.__objects__.star);
			game.newStar();
		}
	},

	successClickCount: 0,
	clickCount: 0,

	__objects__: {
		flames: []
	}
};
