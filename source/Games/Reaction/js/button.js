
var button = {
	init: () => {
		button.elem = document.getElementById("btn");
		button.changeState(button.states['start']);
	},

	changeState: (state) => {
		button.state = state;
		button.elem.innerHTML = state.text;
		button.elem.disabled = state.disabled ? true : false;
		button.elem.onclick = state.click;
	},

	states: {
		'start': {
			text: "Start!",
			click: game.start
		},
		'disabled': {
			text: "Disabled",
			disabled: true
		},
		'end': {
			text: "End This!",
			click: game.end
		}
	}
}
