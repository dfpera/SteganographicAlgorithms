# Steganographic Algorithms
## What is Steganography?
Steganography is the art and science of concealing messages via some multimedia carrier. Digital image-based steganography involves embedding hidden data within the pixels of a “cover image”. Its ever-growing list of applications range from copyright control to companies’ safe circulation of confidential information.

## Project Goal: Implement and Evaluate Current Algorithms
Our team will analyze different approaches to digital image steganography by implementing the algorithms and evaluating their performance using a variety of attacks. Only after completing an exhaustive attack on the simpler algorithms will we begin work on the algorithms that supposedly perform better, but are also more difficult to implement. We hope to find out which algorithms perform better or worse and if any circumstances affect their steganographic output.

## Previous Work
Our project draws upon the work of security researchers, whose proposed algorithms we will be implementing. These works include but are not limited to:

* New image steganographic methods using run-length approach by Chin-Chen Chang, Chih-Yang
Lin, and Yu-Zheng Wang
* A Novel Image Data Hiding Scheme with Diamond Encoding by Ruey-Ming Chao,Hsien-Chu Wu,
Chih-Chiang Lee, and Yen-Ping Chu

## Approach
We plan to implement and evaluate each algorithm fully and exhaustively, in order to test for both internal validity (whether an algorithm performs as well as the authors claim) as well as external validity (whether we are able to replicate the study’s results). This critical approach will allow us to compare findings across studies and with our own.

## Process
**1. Determine cover images and embedded images**
	As with other steganography research, we will use standard 512 × 512 grayscale cover images, downloaded from http://dud.inf.tu-dresden.de/~westfeld/rsp/. The data to be embedded will be binary images, such as a logo and a fingerprint.

**2. Implement LSB algorithm**
	We will begin by embedding our secret data into the cover images using least significant bit (LSB) substitution, the simplest algorithm. N-bit variations will also be implemented.

**3. Analyze, attack, and evaluate algorithm**
	Each variation will then be evaluated using various metrics as applicable (described later in Results). We will also investigate the detectability of each algorithm by “attacking” them with common forensics methods.

**4. Implement other algorithms, and repeat step (3)**
	If time permits, we will proceed by implementing other steganography algorithms, each increasing in difficulty from the last. Our tentative list of algorithms from least to most challenging are as follows:
	* LSB - least challenging, highest priority
	* EMD (Exploiting Modification Direction)
	* OPAP (Optimal Pixel Adjustment Process)
	* BRL (Hiding Bitmap files by Run Length)
	* PVD (Pixel Value Differencing)
	* M-PVD (Modulus Pixel Value Differencing)
	* DE (Diamond Encoding)
	* DCT (Discrete Cosine Transform)
	* DWT (Discrete Wavelet Transform)
	* DE-DWT (DE - Discrete Wavelet Transform) - most challenging, lowest priority
	
**5. Compare results of each implemented algorithm**
	Our findings will allow us to draw conclusions regarding the internal and external validities of the previous research in steganographic algorithms. We will also be able to compare the relative performance of each algorithm, and determine which is the most robust.

## Timeline
| Week        | Progress |
| ----------- | -------- |
| 9 – Nov. 2  | Setup development environment and base code for displaying stego-images |
| 10 – Nov. 9 | Complete implementation of one LSB algorithm |
| 11 – Nov. 16 
(Milestone 2) | Complete implementation and analysis of LSB algorithm and all n-bit variations |
| 12 – Nov. 23 Complete implementation and analysis of other algorithms |
| 13 – Nov.30
(Milestone 3) | Complete polished application that allows the user to compare stego-images produced by different algorithms and their relative performance |

## Results
Results are derived from how each algorithm performs under a steganographic attack. To accomplish this we will use the following analysis techniques:
* PSNR/visual inspection - evaluates image quality and distortion
* Embedding capacity - higher capacity usually comes with higher distortion
* Histograms - find differences in the cover and stego-image, evaluates detectability
* External forensic tool(s) - these tools utilize their own steganographic attacks and will act as a secondary test to confirm our own derived results

A successful project will compare and evaluate two steganographic algorithms exhaustively using the techniques above. Ideally, we would like to obtain the same results found in the research papers, but finding falsities can also be considered good if we can prove why these papers may have inaccurate results.

## Summary
As highlighted in Results, we look to compare each algorithm and verify these analytics with what was found in the research papers. Thus, possible outcomes could be either that we find the results from the papers are correct, or they are not. At which point we’d have to ask ourselves why we got different results and come to our own conclusions of which algorithms are best used for image steganography.