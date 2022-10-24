const canvas = document.getElementById('image-builder')
const ctx = canvas.getContext('2d')
const cube = document.getElementById('cube')
const size = 256
const sideRules = [
  [1, 0, 2], // top
  [1, 2, 0], // front
  [3, 2, 1], // right
  [5, 2, 3], // back
  [0, 2, 5], // left
  [1, 3, 4], // bottom
]
const generateSide = ([ruleR, ruleG, ruleB]) => {
  const imageSrc = new Uint8ClampedArray(size * size * 4)
  for (let i = 0; i < imageSrc.length; i += 4) {
    const [r, g, b, a] = [i, i + 1, i + 2, i + 3]
    const pixelI = i / 4
    const col = pixelI  % size
    const row = Math.floor(pixelI / size)
    const ruleMap = [0, col, row, 0xFF, 0xFF - row, 0xFF - col]
    imageSrc[r] = ruleMap[ruleR]
    imageSrc[g] = ruleMap[ruleG]
    imageSrc[b] = ruleMap[ruleB]
    imageSrc[a] = 255
  }
  ctx.putImageData(new ImageData(imageSrc, size, size), 0, 0)
  const image = new Image(size, size)
  image.src = canvas.toDataURL('image/png')
  return image
}

sideRules
  .map(generateSide)
  .forEach((img, i) => {
    img.classList.add(`side-${i}`);
    cube.appendChild(img);
  });

function rotate(event) {
    var cube = document.getElementById('cube')
    var x = event.clientX - window.innerWidth / 2;
    var y = event.clientY - window.innerHeight / 2;
    var q = 0.15
    var i;
    x = x * q * 1.25
    y = -y * q * 1.25
    children = document.getElementById('cube').children
    cube.style.transform = "rotateY(" + x + "deg) rotateX(" + y + "deg)"
}
document.addEventListener('mousemove', rotate)
