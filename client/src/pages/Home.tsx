import AboutSection from "../components/aboutSection/AboutSection";
import Banner from "../components/banner/Banner";
import ChoosingSection from "../components/choosingSection/ChoosingSection";
import Footer from "../components/footer/Footer";
import Hero from "../components/hero/Hero";
import Navbar from "../components/navbar/Navbar";
import OurServices from "../components/ourServices/OurServices";
import Testimonials from "../components/testimonialsSection/Testimonials";

const Home = () => {
  return (
    <>
      <header>
        <Navbar />
        <Hero />
      </header>
      <main>
        <ChoosingSection />
        <AboutSection />
        <OurServices />
        <Banner />
        <Testimonials />
      </main>
      <footer>
        <Footer />
      </footer>
    </>
  );
};

export default Home;
