public class goodService {
	goodDAO goodDAO = new goodDAO();
	goodDTO goodDTO = new goodDTO();
	
	public void goodup() {
		goodDAO.goodUp();
	}
	
	public goodDTO getGoodCount() {
		int goodCount = goodDAO.getGoodCount();
		goodDTO.setGood(goodCount);
		
		return goodDTO;
	}
	
	
}