function chartCustom(elementsId, chartColor, dataValue, dataMax){
    const elements = document.getElementById(elementsId)
    let elementsWidth = elements.offsetWidth;
    let options = {
        value:  dataValue,
        size: elementsWidth,
        lineWidth: 10,
        rotate: 0,
        max: dataMax,
        units: dataValue,
        totalUnits:'/'+ dataMax
    };

    const html =	'<div class="chart-box">'+
    				'	<canvas></canvas>'+
    				'	<div class="percent"></div>'+
    				'</div>'+
    				'<div class="units-box">'+
    					'<span class="chart-units"></span>'+
                        '<span class="total-units"></span>'+
    				'</div>'
    				
	elements.innerHTML = html;
	
    const canvas = elements.querySelector('canvas');
    const percent = elements.querySelector('.percent');
    const units = elements.querySelector('.chart-units');
    const totalUnits = elements.querySelector('.total-units');
    
    units.textContent = options.units;
    totalUnits.textContent = options.totalUnits;
    
    const ctx = canvas.getContext('2d');
    canvas.width = canvas.height = options.size;
    elements.querySelector('.chart-box').style.height = options.size + 'px';
    percent.textContent = (dataValue / dataMax * 100).toFixed(0) + '%';
	elements.querySelector('.chart-units').style.color = chartColor;
	elements.querySelector('.percent').style.color = chartColor;
    
    ctx.translate(options.size / 2, options.size / 2);
    ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI);
    
    const radius = (options.size - options.lineWidth) / 2;
    
    function drawCircle(color, lineWidth, value) {
        value = Math.min(Math.max(0, value || 1), 1);
        ctx.beginPath();
        ctx.arc(0, 0, radius, 0, Math.PI * 2 * value, false);
        ctx.strokeStyle = color;
        ctx.lineCap = 'round';
        ctx.lineWidth = lineWidth;
        ctx.stroke();
    };
    
    drawCircle('#f1f1f1', options.lineWidth, 100 / 100);
    drawCircle(chartColor, options.lineWidth, options.value / options.max);   
}