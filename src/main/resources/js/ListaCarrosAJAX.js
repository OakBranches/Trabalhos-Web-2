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
    url += "?term=" + modelo;
    xmlHttp.onreadystatechange = () => atualizaTabelaCarros(context);
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);

}

function atualizaTabelaCarros(context) {
    if (xmlHttp.readyState === 4 || xmlHttp.readyState === "complete") {
        var carros = JSON.parse(xmlHttp.responseText);

        // CRIA UMA TABELA DINAMICA
        var sessionVar = document.getElementById('propor').value;
        console.log("propor "+ sessionVar);
        var oldtbody = document.getElementById("tbody");
        var table = document.createElement("tbody");
        table.setAttribute("id", "tbody");


        table.border = "1";
        table.style.border = "1px solid black";
        table.style.width = "400px";


        // CRIA DEMAIS LINHAS COM OS VALORES

        for (var i = 0; i < carros.length; i++) {



            var tmp = carros[i];
            var lista = tmp.split(";");
            var id = lista[0];
            var modelo = lista[1];
            tr = table.insertRow(-1);
            var cellimg = tr.insertCell(-1);
            cellimg.setAttribute("id", "image"+id);
            var cnpj = lista[2];
            var placa = lista[3];
            var chassi = lista[4];
            var descricao = lista[5];
            var ano = lista[6];
            var km = lista[7];
            var valor = lista[8];
            var listaImagens = lista[9].replace("[","").replace("]","").split(",");
            var externalCarousel = document.createElement("div");
            externalCarousel.setAttribute("id","carouselExampleControls" + id);
            externalCarousel.setAttribute("class","carousel slide");
            externalCarousel.setAttribute("data-bs-ride","carousel");
            externalCarousel.setAttribute("style","width: 300px")
            var internalCarousel = document.createElement("div");
            internalCarousel.setAttribute("class", "carousel-inner");
            let count = 0;
            listaImagens.forEach((item, index) => {
                if(item.length > 0) {
                    count += 1;
                    var div_carousel = document.createElement("div");
                    var classe = "carousel-item";
                    if (count == 1)
                        classe += " active"
                    div_carousel.setAttribute("class", classe);
                    var img = document.createElement("img");
                    img.setAttribute("src", item);
                    img.setAttribute("class", "d-block w-100")
                    img.setAttribute("height", "200px");
                    img.setAttribute("alt", "...");
                    div_carousel.appendChild(img);
                    internalCarousel.appendChild(div_carousel);
                }
            })

            if (count > 0) {
                externalCarousel.appendChild(internalCarousel);

                var button1 = document.createElement("button");
                button1.setAttribute("class","carousel-control-prev");
                button1.setAttribute("type","button");
                button1.setAttribute("data-bs-target","#carouselExampleControls"+ id);
                button1.setAttribute("data-bs-slide","prev");
                var span1 = document.createElement("span");
                span1.setAttribute("class","carousel-control-prev-icon");
                span1.setAttribute("aria-hidden","true");
                var span2 = document.createElement("span");
                span2.setAttribute("class","visually-hidden")
                span2.innerHTML = "Previous"
                button1.appendChild(span1);
                button1.appendChild(span2);
                externalCarousel.appendChild(button1);

                var button2 = document.createElement("button");
                button2.setAttribute("class","carousel-control-next");
                button2.setAttribute("type","button");
                button2.setAttribute("data-bs-target","#carouselExampleControls"+ id);
                button2.setAttribute("data-bs-slide","next");
                var span3 = document.createElement("span");
                span3.setAttribute("class","carousel-control-next-icon");
                span3.setAttribute("aria-hidden","true");
                var span4 = document.createElement("span");
                span4.setAttribute("class","visually-hidden")
                span4.innerHTML = "Next"
                button2.appendChild(span3);
                button2.appendChild(span4);
                externalCarousel.appendChild(button2);
                cellimg.appendChild(externalCarousel);

            }

            var col2 = tr.insertCell(-1);
            col2.style.textAlign = "center";
            col2.innerHTML = modelo;

            var col3 = tr.insertCell(-1);
            col3.style.textAlign = "center";
            col3.innerHTML = placa;

            var col4 = tr.insertCell(-1);
            col4.style.textAlign = "center";
            col4.innerHTML = chassi;

            var col5 = tr.insertCell(-1);
            col5.style.textAlign = "center";
            col5.innerHTML = descricao;

            var col6 = tr.insertCell(-1);
            col6.style.textAlign = "center";
            col6.innerHTML = ano;

            var col7 = tr.insertCell(-1);
            col7.style.textAlign = "center";
            col7.innerHTML = km;

            var col8 = tr.insertCell(-1);
            col8.style.textAlign = "center";
            col8.innerHTML = valor;
            if (sessionVar !== "false") {
                var col9 = tr.insertCell(-1);
                col9.style.textAlign = "center";

                var a = document.createElement("a");
                a.setAttribute("href", context+"/proposta/"+id);
                a.innerHTML = "+";
                col9.appendChild(a);
            }
        }


        // Pega o span COM A QUANTIDADE DE CARROS

        var p = document.getElementById('qtd');
        p.innerHTML = carros.length;


        oldtbody.parentNode.replaceChild(table,oldtbody);
    }
}