const canvas = document.getElementById("theGame");
canvas.setAttribute('width', '1408');
canvas.setAttribute('height', '1536');
const ctx = canvas.getContext("2d");

const ground = new Image();
ground.src = "img/ground.png";

const foodImg = new Image();
foodImg.src = "img/stop64.png";

const crashImg = new Image();
crashImg.src = "img/crash64.png";

const crashLastImg = new Image();
crashLastImg.src = "img/crash__last64.png";

const crashBusImg = new Image();
crashBusImg.src = "img/crash-bus-64.png";

let tailImg = new Image();
tailImg.src = "img/passenger64.png";

let bodyImg = new Image();
bodyImg.src = "img/passenger__last64.png";

let snakeImg = new Image();
snakeImg.src = "img/bus64.svg";

let interval = 200;
//Скорость игры

let box = 64;
let score = 0;
let food = generateFood();

let snake = [];
snake[0]  = {
	x: 10 * box,
	y: 13 * box
};

document.addEventListener("keydown", direction);

let dir;

function direction(event) {
// Назначения клавиш
	if(event.keyCode == 37 && dir != "right")
		dir = "left";
	else if(event.keyCode == 38 && dir != "down")
		dir = "up";
	else if(event.keyCode == 39 && dir != "left")
		dir = "right";
	else if(event.keyCode == 40 && dir != "up")
		dir = "down";
}

function death() {
//Функция смерти
	renderCrash();
	clearInterval(game);
}

function renderCrash() {
//Последствия встречи со стеной
	tailImg = crashImg;
	bodyImg = crashLastImg;
	snakeImg = crashBusImg;
	render();
}

function cannibalism(body, arr) {
//Ограничение поедания собственного тела
	for(let i = 0; i < arr.length; i++) {
		if(body.x == arr[i].x && body.y == arr[i].y) {
			death();
			return;
		}
	}
}

function generateFood() {
//Случайная генерация еды на поле
	return {
		x: Math.floor((Math.random() * 19 + 1)) * box, 
		//Координаты поля по оси X
		y: Math.floor((Math.random() * 19 + 3)) * box, 
		//Координаты поля по оси Y
	};
}

function render() {
//Отрисовка элементов
	ctx.drawImage(ground, 0, 0);
	ctx.drawImage(foodImg, food.x, food.y);
	
	ctx.drawImage(snakeImg, snake[0].x, snake[0].y);

	for (let i = 1; i < snake.length - 1; i++) {
		ctx.drawImage(tailImg, snake[i].x, snake[i].y);
	}
	if (snake.length > 1) {
		ctx.drawImage(bodyImg, snake[snake.length - 1].x, snake[snake.length - 1].y);
	}

	ctx.fillStyle = "black";
	ctx.font = "50px Consolas";
	ctx.fillText(score, box * 2.5, box * 1.5);	
}


function tickGame() {
//Логика игры
	let head = Object.assign({}, snake[0]);

	if(head.x < box * 1 || head.x > box * 20
	 	|| head.y < 3 * box || head.y > box * 22) {
	 	death();
		return;
	}


	render();

	if(head.x == food.x && head.y == food.y) {
		score++;
		// interval = interval * ((10/11) / Math.log(score + 1));
		interval = interval * Math.sqrt(Math.log(2*0.9)/Math.log(3))+(2 * Math.exp(2));
		clearInterval(game);
		game = setInterval(tickGame, interval);

		food = generateFood();
	} else {
		snake.pop();
	}

	if(dir == "left") head.x -= box;
	if(dir == "right") head.x += box;
	if(dir == "up") head.y -= box;
	if(dir == "down") head.y += box;

	cannibalism(head, snake);
	snake.unshift(head);
}

let game = setInterval(tickGame, interval);