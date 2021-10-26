package br.ufscar.dc.dsw1.services.spec;

import java.util.List;

import br.ufscar.dc.dsw1.domain.FileEntity;

public interface IFileService {

	public FileEntity salvar(FileEntity file);	
	
	public void excluir(Long id);
	
	public FileEntity buscarPorId(Long id); 

	public List<FileEntity> buscarTodos();
}