$(document).ready(function() {
   
    setTimeout(function() {
        $("#alert").slideUp(500);
    }, 3000);

   
    var itensPorPagina = 5;
    var paginaAtual = 1;
    var todasAsLinhas = $("table tbody tr");
    var linhasFiltradas = todasAsLinhas;

    function atualizarTabela() {
        var totalItens = linhasFiltradas.length;
        var totalPaginas = Math.ceil(totalItens / itensPorPagina) || 1;
        
        if (paginaAtual > totalPaginas) paginaAtual = totalPaginas;

        todasAsLinhas.hide();
        var inicio = (paginaAtual - 1) * itensPorPagina;
		
        var fim = inicio + itensPorPagina;
        linhasFiltradas.slice(inicio, fim).show();

        var paginacaoHtml = '';
        paginacaoHtml += '<li class="' + (paginaAtual === 1 ? 'disabled' : '') + '"><a href="#" data-page="' + (paginaAtual - 1) + '">&lt; Anterior</a></li>';
        
        for (var i = 1; i <= totalPaginas; i++) {
            paginacaoHtml += '<li class="' + (paginaAtual === i ? 'active' : '') + '"><a href="#" data-page="' + i + '">' + i + '</a></li>';
        }
        
        paginacaoHtml += '<li class="' + (paginaAtual === totalPaginas ? 'disabled' : '') + '"><a href="#" data-page="' + (paginaAtual + 1) + '">Próximo &gt;</a></li>';
        
        $(".pagination").html(paginacaoHtml);
    }

    $("#search").on("keyup", function() {
        var termo = $(this).val().toLowerCase();
        linhasFiltradas = todasAsLinhas.filter(function() {
            return $(this).text().toLowerCase().indexOf(termo) > -1;
        });
        paginaAtual = 1;
        atualizarTabela();
    });

    $(document).on("click", ".pagination li:not(.disabled) a", function(e) {
        e.preventDefault();
        paginaAtual = parseInt($(this).attr("data-page"));
        atualizarTabela();
    });

    atualizarTabela();
});