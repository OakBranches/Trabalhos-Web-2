package br.ufscar.dc.dsw1.services.spec;

import br.ufscar.dc.dsw1.domain.Carro;

import java.util.List;


public interface ICarroService {
    Carro salvar(Carro s);
    Carro buscaPorId(Long Id);
    void excluir(Long id);
    List<Carro> buscarTodos();
    List<Carro> buscarTodosComNome(String nome);
    List<Carro> buscarTodosDaLoja(String cnpj);
    List<Carro> buscarTodosDaLoja(Long id);

}
