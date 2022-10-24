var colorPicker = document.getElementById("colorByMouse")
colorPicker.addEventListener("input", updateFirst, false);
RGB = colorPicker.value.substring(1).split(/([^][^])/).filter(function(x){return x}).map((elem) => parseInt(elem,16))
insertRGB(RGB);
insertHSV(RGB);
insertCMYK(RGB);

var inputs = Array.from(
    document.getElementsByClassName('colorInputs')
    ).map((elem) => 
    Array.from(elem.children)
    ).flat().forEach((element) => {
        element.addEventListener('input', updateAll, false)
    }) 

function updateFirst(event){
    RGB = event.target.value.substring(1).split(/([^][^])/).filter(function(x){return x}).map((elem) => parseInt(elem,16))
    insertRGB(RGB);
    insertHSV(RGB);
    insertCMYK(RGB);
}

function insertRGB(rgb) {
    document.getElementById("R").value = parseInt(rgb[0])
    document.getElementById("G").value = parseInt(rgb[1])
    document.getElementById("B").value = parseInt(rgb[2])
}

function insertHSV(rgb){
    hsv = rgb2hsv(...rgb)
    document.getElementById("H").value = hsv[0]
    document.getElementById("S").value = hsv[1]
    document.getElementById("V").value = hsv[2]
}

function insertCMYK(rgb){
    cmyk = rgb2cmyk(...rgb)
    document.getElementById("C").value = cmyk[0]
    document.getElementById("M").value = cmyk[1]
    document.getElementById("Y").value = cmyk[2]
    document.getElementById("K").value = cmyk[3]
}

// input: r,g,b in [0,1], out: h in [0,360) and s,v in [0,1]
function rgb2hsv(r,g,b) {
    let v=Math.max(r,g,b), c=v-Math.min(r,g,b);
    let h= c && ((v==r) ? (g-b)/c : ((v==g) ? 2+(b-r)/c : 4+(r-g)/c)); 
    return [60*(h<0?h+6:h), v&&c/v, v];
  }

      /*
K = 1-max(R', G', B')

The cyan color (C) is calculated from the red (R') and black (K) colors:

C = (1-R'-K) / (1-K)

The magenta color (M) is calculated from the green (G') and black (K) colors:

M = (1-G'-K) / (1-K)

The yellow color (Y) is calculated from the blue (B') and black (K) colors:

Y = (1-B'-K) / (1-K)
    */
function rgb2cmyk(r,g,b){
    noR = r/255;
    noG = g/255
    noB = b/255;
    K = 1-Math.max(noR, noG, noB)
    C = (1 - noR - K)/ (1-K)
    C = +C || 0
    M = (1 - noG - K)/ (1-K)
    M = +M || 0
    Y = (1 - noB - K)/ (1-K)
    Y = +Y || 0
    return [C,M,Y,K]
}


function updateAll(event){
    switch (event.target.name) {
        case 'RGB':
            RGB = Array.from(document.getElementsByName('RGB')).map((elem) => elem.value || 0)
            insertHSV(RGB);
            insertCMYK(RGB);
            changeColorPicker(RGB)
            break;
        case 'HSV':
          RGB =  hsv2rgb(Array.from(document.getElementsByName('HSV')).map((elem) => elem.value || 0))
          insertRGB(RGB);
          insertCMYK(RGB);
          changeColorPicker(RGB)
          break;
        case 'CMYK':
            RGB = cmyk2rgb(Array.from(document.getElementsByName('CMYK')).map((elem) => elem.value || 0))
          console.log('Mangoes and papayas are $2.79 a pound.');
          break;
        default:
          console.log(`Sorry.`);
      }
}

function changeColorPicker(RGB){
    document.getElementById("colorByMouse").value = "#"+RGB.map((elem=> {
        numb = Number(parseInt(elem)).toString(16).toUpperCase()
        switch(numb.length){
            case 0:
                numb = '00'
                break;
            case 1:
                numb = "0" + numb;
                break;
        }
        return numb
    })).join('')
}


function hsv2rgb(hsv){
    M = 255 * +hsv[2]
    m = M* (1- +hsv[1])
    z = (M-m) * (1 - Math.abs((+hsv[0]/60) % 2 - 1))
    m = +hsv[2] - +hsv[1]
    rgb = []
    h = +hsv[0]
    switch(true){
        case 0 <= h && h < 60:
            rgb = [M,z+m,m]
            break
        case 60 <= h && h < 120:
            rgb = [z+m,M,m]
            break
        case 120 <= h && h < 180:
            rgb = [m,M,z+m]
            break
        case 180 <=h && h < 240:
            rgb = [m,z+m,M]
            break
        case 240 <= h && h < 300:
            rgb = [z+m,m,M]
            break
        case 300 <= h && h < 360:
            rgb = [M,m,z+m]
            break
    }
    return rgb
}