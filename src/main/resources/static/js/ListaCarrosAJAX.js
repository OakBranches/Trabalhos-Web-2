var xmlHttp;

function apresenta(selCidade){
    alert("Selecionado: " + selCidade.target.value);
}

function getCarros(context) {
    var cidade = document.getElementById("carro");
    var modelo = carro.value;
    if (typeof XMLHttpRequest !== "undefined") {
        xmlHttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlHttp === null) {
        alert("Browser does not support XMLHTTP Request");
        return;
    }

    var url = "results";
    url += "/"+ modelo;
    xmlHttp.onreadystatechange = () => atualizaTabelaCarros(context);
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);

}

function atualizaTabelaCarros(context) {
    if (xmlHttp.readyState === 4 || xmlHttp.readyState === "complete") {
        console.log(xmlHttp.responseText);
        //todo refazer a lógica







        //
        var carros = JSON.parse(xmlHttp.responseText);
        console.log(carros[0]);
        // //
        let propor = false;
        // // // CRIA UMA TABELA DINAMICA
        var sessionVar = document.getElementById('propor');
        if (sessionVar != null){
            propor = true;
        }
        // console.log("propor "+ sessionVar);
        var oldtbody = document.getElementById("tbody");
        var table = document.createElement("tbody");
        table.setAttribute("id", "tbody");
        // CRIA DEMAIS LINHAS COM OS VALORES
        let iHtml = ""

        for (let i = 0; i < carros.length; i++) {
            let carro = carros[i]
            iHtml += `<tr><td>`
            if (carro['imagens'].length === 0){
                iHtml+='<span>Não há Imagem</span>'
            }else {
                iHtml+= `<div id="${'carouselExampleControls'+ carro['id']}" class="carousel slide" data-bs-ride="carousel" style="width: 300px"><div class="carousel-inner">`
                for (let j = 0; j < carro['imagens'].length; j++) {
                    iHtml+= `<div class="${'carousel-item' +  (j === 0? ' active':'')}">`
                    iHtml+= `<img src="/download/${carro['imagens'][j]['id']}" width="160px"/></div>`
                }
                iHtml +=`</div><button class="carousel-control-prev" type="button" data-bs-target="${'#carouselExampleControls'+ carro['id']}" data-bs-slide="prev">`
                iHtml += `<span class="carousel-control-prev-icon" aria-hidden="true"></span>`
                iHtml += `<span class="visually-hidden">Previous</span></button>`
                iHtml += `<button class="carousel-control-next" type="button" data-bs-target="${'#carouselExampleControls'+ carro['id']}" data-bs-slide="next">`
                iHtml += ` <span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Next</span></button></div>`
            }
            iHtml+= `</td>`
            iHtml+= `<td> <span>${carro['modelo']}</span></td>`
            iHtml+= `<td> <span>${carro['placa']}</span></td>`
            iHtml+= `<td> <span>${carro['chassi']}</span></td>`
            iHtml+= `<td> <span>${carro['descricao']}</span></td>`
            iHtml+= `<td> <span>${carro['ano']}</span></td>`
            iHtml+= `<td> <span>${carro['km']}</span></td>`
            iHtml+= `<td> <span>${carro['valor']}</span></td>`
            iHtml+= `<td> <span>${carro['loja']}</span></td>`
            if (propor){
                iHtml+= `<td><a class="btn btn-info btn-sm" href="/proposta/create/${carro['id']}" role="button">`
                iHtml+= `<ion-icon name="bookmark-outline"></ion-icon></a></td>`
            }
            iHtml+='</tr>'
        }

        table.innerHTML = iHtml;

        // Pega o span COM A QUANTIDADE DE CARROS

        let p = document.getElementById('qtd');
        p.innerHTML = carros.length;

        oldtbody.parentNode.replaceChild(table,oldtbody);
    }
}