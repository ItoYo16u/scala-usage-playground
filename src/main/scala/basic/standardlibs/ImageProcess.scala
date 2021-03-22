package basic.standardlibs
import java.awt.{Color, Font, RenderingHints}
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import java.nio.file.Path
import javax.imageio.{IIOImage, ImageIO, ImageWriteParam}
object ImageProcess extends  App {
  val originalImage = ImageIO.read(Path.of("./hoge.png").toFile)
  val newImage = new BufferedImage(originalImage.getWidth+30,originalImage.getHeight,originalImage.getType)
  val graphics = newImage.createGraphics()
  graphics.drawImage(originalImage,0,0,null)
  graphics.setColor(Color.DARK_GRAY)
  graphics.fillRect(originalImage.getWidth,0,originalImage.getWidth+30,originalImage.getHeight)
  graphics.setFont(new Font("Arial",Font.ITALIC,16))
  graphics.setColor(Color.BLACK)
  graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
  graphics.drawString("hello world",originalImage.getWidth+5,originalImage.getHeight/2)
  val out = new FileOutputStream("./output_file_name.jpg")
  val in = ImageIO.createImageOutputStream(out)
  val writer = ImageIO.getImageWritersByFormatName("jpeg").next()
  val params = writer.getDefaultWriteParam
  params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
  params.setCompressionQuality(1.0f)
  writer.setOutput(in)
  writer.write(null,new IIOImage(newImage,null,null),params)
  writer.dispose()
}