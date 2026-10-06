package com.example.data

import com.example.model.HazardLevel
import com.example.model.ItemCategory
import com.example.model.ScanResultItem

object ScanKnowledgeBase {

    val items: List<ScanResultItem> = listOf(
        // --- PLANTAS Y MATAS ---
        ScanResultItem(
            id = "plant_adelfa",
            name = "Adelfa / Laurel de Flor (Mata)",
            scientificOrChemicalName = "Nerium oleander",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 96,
            isHarmful = true,
            summary = "EXTREMADAMENTE VENENOSA. Todas las partes de la mata contienen glucósidos cardíacos letales.",
            humanHazard = "Muy peligroso. El consumo de una sola hoja puede causar arritmias cardíacas graves, paro cardíaco, vómitos intensos e hipotensión fatal.",
            petHazard = "Mortal para perros y gatos. La savia y las hojas provocan salivación espumosa, convulsiones y muerte en pocas horas.",
            activeToxins = listOf("Oleandrina", "Neriina", "Glucósidos cardíacos"),
            commonSymptoms = listOf("Arritmia cardíaca", "Vómitos violentos", "Bradicardia", "Pupilas dilatadas", "Colapso"),
            firstAid = "ACUDIR DE INMEDIATO A URGENCIAS MÉDICAS O VETERINARIAS. No inducir el vómito sin indicación médica.",
            safeHandlingTips = "Usar guantes gruesos para podar. Nunca quemar sus ramas (el humo es tóxico). Mantener alejado de niños y mascotas.",
            commonUses = "Arbusto ornamental de exterior en parques y jardines."
        ),
        ScanResultItem(
            id = "plant_monstera",
            name = "Monstera Deliciosa (Costilla de Adán)",
            scientificOrChemicalName = "Monstera deliciosa",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.HARMFUL,
            toxicityScore = 65,
            isHarmful = true,
            summary = "DAÑINA PARA MASCOTAS Y NIÑOS. Sus hojas y tallos contienen cristales afilados de oxalato de calcio.",
            humanHazard = "Moderadamente irritante en humanos. Masticar la hoja causa ardor severo en lengua, labios y garganta, e hinchazón.",
            petHazard = "Tóxica para gatos y perros. Provoca dolor oral agudo, babeo incesante, dificultad para tragar e irritación estomacal.",
            activeToxins = listOf("Cristales de oxalato de calcio insolubles"),
            commonSymptoms = listOf("Ardor bucal severo", "Babeo excesivo", "Hinchazón en labios y lengua", "Vómito leve"),
            firstAid = "Lavar la boca con agua fría o leche para disolver cristales. Ofrecer hielo o yogur. Si hay asfixia por inflamación, ir al hospital.",
            safeHandlingTips = "Ubicar en estantes altos fuera del alcance de perros y gatos curiosos. Lavar manos tras podar.",
            commonUses = "Planta de interior muy popular por su follaje decorativo."
        ),
        ScanResultItem(
            id = "plant_dieffenbachia",
            name = "Dieffenbachia (Amoena / Mata Muda)",
            scientificOrChemicalName = "Dieffenbachia seguine",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 88,
            isHarmful = true,
            summary = "ALTAMENTE TÓXICA. La savia provoca parálisis temporal de cuerdas vocales e inflamación de la glotis.",
            humanHazard = "Grave en niños y adultos. La masticación produce edema lingual inmediato e incapacidad temporal para hablar o respirar.",
            petHazard = "Peligro severo en perros y gatos: quemaduras orales químicas, asfixia e inflamación esofágica.",
            activeToxins = listOf("Oxalato cálcico en rafídeos", "Enzima proteolítica dumbcaína"),
            commonSymptoms = listOf("Pérdida de la voz", "Asfixia por edema", "Salivación profusa", "Úlceras bucales"),
            firstAid = "Enjuagar boca inmediatamente con agua continua. No ingerir sólidos. Acudir al hospital si hay dificultad respiratoria.",
            safeHandlingTips = "Manipular siempre con guantes de látex o nitrilo. Desaconsejada en hogares con niños pequeños.",
            commonUses = "Planta ornamental de follaje variegado para salas."
        ),
        ScanResultItem(
            id = "plant_sansevieria",
            name = "Lengua de Suegra (Sansevieria / Mata de Espada)",
            scientificOrChemicalName = "Dracaena trifasciata",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.CAUTION,
            toxicityScore = 40,
            isHarmful = true,
            summary = "MODERADAMENTE TÓXICA PARA MASCOTAS. Contiene saponinas hemolíticas y de sabor amargo.",
            humanHazard = "Bajo peligro en humanos adultos. En niños puede provocar dolor de estómago leve y náuseas si se mastica en cantidad.",
            petHazard = "Tóxica para gatos y perros. Ocasiona náuseas, vómitos, diarrea y letargo leve si muerden las hojas.",
            activeToxins = listOf("Saponinas", "Ácidos orgánicos amargos"),
            commonSymptoms = listOf("Salivación", "Vómitos", "Diarrea leve", "Falta de apetito transitoria"),
            firstAid = "Retirar restos vegetales de la boca. Proporcionar agua fresca e hidratación. Consultar al veterinario si persiste.",
            safeHandlingTips = "Fácil de cuidar, pero colocar en zonas inaccesibles para mascotas que tiendan a morder plantas.",
            commonUses = "Purificación de aire de interiores, planta rústica resistente."
        ),
        ScanResultItem(
            id = "plant_ricino",
            name = "Mata de Ricino / Higuera del Diablo",
            scientificOrChemicalName = "Ricinus communis",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 99,
            isHarmful = true,
            summary = "EXTREMADAMENTE LETAL. Sus semillas contienen Ricina, una de las toxinas biológicas más potentes del planeta.",
            humanHazard = "Mortal. Ingerir 3 a 5 semillas masticadas puede causar la muerte por fallo multiorgánico en pocas horas.",
            petHazard = "Mortal para animales. Causa necrosis intestinal, fallo renal y hepático agudo con alta tasa de mortalidad.",
            activeToxins = listOf("Ricina (ribosoma inactivadora de tipo 2)", "Ricilina"),
            commonSymptoms = listOf("Gastroenteritis hemorrágica", "Fallo renal", "Deshidratación extrema", "Shock y coma"),
            firstAid = "EMERGENCIA MÉDICA ABSOLUTA. Traslado inmediato a la UCI toxicológica con muestras de la semilla.",
            safeHandlingTips = "Prohibido cultivar cerca de ganado, niños o mascotas. Desechar semillas con protección extrema.",
            commonUses = "Planta silvestre e industrial para extracción de aceite técnico."
        ),
        ScanResultItem(
            id = "plant_pothos",
            name = "Poto / Pothos Colgante (Mata Trepadora)",
            scientificOrChemicalName = "Epipremnum aureum",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.CAUTION,
            toxicityScore = 48,
            isHarmful = true,
            summary = "DAÑINA PARA GATOS Y PERROS. Causa irritación mecánica en mucosas por oxalatos.",
            humanHazard = "Irritación localizada en labios y garganta si se mastica. Rara vez grave en adultos.",
            petHazard = "Causa irritación oral y vómito en felinos y cachorros que mastiquen los tallos colgantes.",
            activeToxins = listOf("Oxalato de calcio"),
            commonSymptoms = listOf("Babeo", "Frotarse la boca con las patas", "Molestia estomacal"),
            firstAid = "Enjuagar boca y suministrar líquidos frescos. Mantener en observación.",
            safeHandlingTips = "Colgar en macetas suspendidas en techos o repisas altas.",
            commonUses = "Planta colgante de fácil mantenimiento y gran belleza."
        ),
        ScanResultItem(
            id = "plant_aloe_vera",
            name = "Aloe Vera (Sábila)",
            scientificOrChemicalName = "Aloe barbadensis miller",
            category = ItemCategory.PLANT,
            hazardLevel = HazardLevel.CAUTION,
            toxicityScore = 25,
            isHarmful = false,
            summary = "SEGURO EL GEL EN HUMANOS; LA CORTEZA ES TÓXICA PARA MASCOTAS por la aloína amarilla.",
            humanHazard = "El gel transparente es seguro y medicinal. La savia amarilla (aloína) bajo la piel es un laxante potente e irritante.",
            petHazard = "Tóxico para perros y gatos si comen la hoja entera (aloína causa diarrea con sangre y letargo).",
            activeToxins = listOf("Aloína (antraquinonas amargas en la corteza)"),
            commonSymptoms = listOf("Diarrea", "Orina rojiza en mascotas", "Temblores leves"),
            firstAid = "Para mascotas: retirar planta y acudir al veterinario si hay diarrea continua. En humanos: lavar la pulpa antes de usar.",
            safeHandlingTips = "Para uso cutáneo o gastronómico humano, pelar bien y eliminar la savia amarilla lavando el cristal.",
            commonUses = "Cicatrizante tópico, hidratante cosmético y quemaduras solares."
        ),

        // --- QUÍMICOS DEL HOGAR Y LIMPIEZA ---
        ScanResultItem(
            id = "chem_bleach",
            name = "Lejía / Cloro / Lavandina",
            scientificOrChemicalName = "Hipoclorito de Sodio (NaClO ~5%)",
            category = ItemCategory.HOUSEHOLD_CHEMICAL,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 90,
            isHarmful = true,
            summary = "CORROSIVO Y PELIGROSO. Nunca mezclar con amoníaco ni salfumán (produce gas cloro mortal).",
            humanHazard = "Provoca quemaduras químicas en piel y ojos. La inhalación de vapores quema el tracto respiratorio. Ingesta altamente destructiva.",
            petHazard = "Muy peligroso. Las almohadillas se queman y el lamer residuos produce úlceras orales severas.",
            activeToxins = listOf("Hipoclorito sódico", "Cloramina gaseosa si se mezcla"),
            commonSymptoms = listOf("Tos sofocante", "Quemaduras de piel", "Ceguera temporal", "Dolor epigástrico abrasador"),
            firstAid = "Contacto ocular/piel: lavar con agua corriente 15-20 min ininterrumpidos. Ingesta: NUNCA inducir el vómito ni neutralizar con ácidos.",
            safeHandlingTips = "Ventilar bien habitaciones. Usar guantes y gafas protectoras. Guardar cerrado con seguro para niños.",
            commonUses = "Desinfección de baños y blanqueo de ropa blanca."
        ),
        ScanResultItem(
            id = "chem_sosa",
            name = "Sosa Cáustica (Desatascador de Tuberías)",
            scientificOrChemicalName = "Hidróxido de Sodio (NaOH)",
            category = ItemCategory.HOUSEHOLD_CHEMICAL,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 98,
            isHarmful = true,
            summary = "CORROSIVO EXTREMO. Causa necrosis por licuefacción inmediata en tejidos vivos.",
            humanHazard = "Destruye la córnea en segundos, provocando ceguera permanente. La ingestión perfora el esófago y estómago de forma irreversible.",
            petHazard = "Mortal y desastroso al menor contacto con patas o lengua.",
            activeToxins = listOf("Base fuerte alcalina (pH > 13)"),
            commonSymptoms = listOf("Dolor abrasador", "Destrucción de tejido", "Shock hipovolémico", "Perforación esofágica"),
            firstAid = "LLAMAR A URGENCIAS INMEDIATAMENTE. Lavar con agua a chorro abundante. NO dar vinagre ni inducir el vómito.",
            safeHandlingTips = "Uso profesional estricto: gafas herméticas, guantes de caucho grueso. Almacenaje bajo llave.",
            commonUses = "Desatascar tuberías colapsadas y fabricación de jabón artesanal."
        ),
        ScanResultItem(
            id = "chem_ammonia",
            name = "Amoníaco Doméstico",
            scientificOrChemicalName = "Hidróxido de Amonio (NH4OH)",
            category = ItemCategory.HOUSEHOLD_CHEMICAL,
            hazardLevel = HazardLevel.HARMFUL,
            toxicityScore = 78,
            isHarmful = true,
            summary = "GAS IRRITANTE Y CORROSIVO. Vapores altamente tóxicos para asmáticos y animales.",
            humanHazard = "Irrita gravemente los ojos, nariz y garganta. La exposición prolongada puede desencadenar edema pulmonar.",
            petHazard = "Los gatos son especialmente sensibles a los vapores, sufriendo espasmos bronquiales graves.",
            activeToxins = listOf("Amoníaco volátil libre"),
            commonSymptoms = listOf("Lagrimeo intenso", "Sensación de asfixia", "Quemadura en mucosas"),
            firstAid = "Trasladar al aire libre inmediatamente. Enjuagar ojos con agua abundante.",
            safeHandlingTips = "Usar en áreas muy ventiladas. NUNCA mezclar con lejía.",
            commonUses = "Desengrasante de cristales, hornos y alfombras."
        ),
        ScanResultItem(
            id = "chem_detergent_pods",
            name = "Cápsulas de Detergente Líquido (Pods)",
            scientificOrChemicalName = "Tensioactivos concentrados en PVA",
            category = ItemCategory.HOUSEHOLD_CHEMICAL,
            hazardLevel = HazardLevel.HARMFUL,
            toxicityScore = 72,
            isHarmful = true,
            summary = "ALTO RIESGO PARA NIÑOS PEQUEÑOS Y MASCOTAS. Muy concentrado.",
            humanHazard = "La membrana se disuelve en la boca liberando líquido cáustico: neumonía por aspiración química y asfixia en bebés.",
            petHazard = "Peligro severo si muerden la cápsula jugando.",
            activeToxins = listOf("Sulfatos de alquilo", "Etoxilatos", "Enzimas proteolíticas concentradas"),
            commonSymptoms = listOf("Vómito violento", "Espuma bucal", "Tos y dificultad respiratoria", "Quemadura corneal"),
            firstAid = "Lavar cara y boca. Si hubo ingestión o tos, acudir al hospital sin esperar.",
            safeHandlingTips = "Guardar en armarios altos con pestillo infantil. Nunca dejar al alcance visual.",
            commonUses = "Lavado de ropa en lavadora automática."
        ),
        ScanResultItem(
            id = "chem_vinegar",
            name = "Vinagre Blanco de Limpieza",
            scientificOrChemicalName = "Ácido acético diluido (6-8%)",
            category = ItemCategory.HOUSEHOLD_CHEMICAL,
            hazardLevel = HazardLevel.SAFE,
            toxicityScore = 15,
            isHarmful = false,
            summary = "SEGURO Y BIODEGRADABLE. Irritante ocular leve por su acidez natural.",
            humanHazard = "No tóxico. Puede provocar ligera irritación si salpica directamente en los ojos.",
            petHazard = "Seguro. El olor suele ser repelente natural para perros y gatos.",
            activeToxins = listOf("Ácido acético de grado alimentario/limpieza"),
            commonSymptoms = listOf("Ardor ocular transitorio si hay contacto directo"),
            firstAid = "Enjuagar ojos con agua limpia por 2 minutos.",
            safeHandlingTips = "Excelente alternativa ecológica a limpiadores tóxicos. No mezclar con lejía.",
            commonUses = "Eliminar cal, brillo en cristales y neutralizar olores."
        ),

        // --- ALIMENTOS Y HONGOS ---
        ScanResultItem(
            id = "food_amanita_phalloides",
            name = "Amanita Phalloides (Cicuta Verde / Hongo Letal)",
            scientificOrChemicalName = "Amanita phalloides",
            category = ItemCategory.FOOD_MUSHROOM,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 100,
            isHarmful = true,
            summary = "VENENO MORTAL. Responsable de más del 90% de muertes por consumo de setas en el mundo.",
            humanHazard = "Mortal. Media seta destruye el hígado en 48 horas. Período de latencia engañoso de 6 a 12 horas sin síntomas antes de la catástrofe.",
            petHazard = "Mortal para cualquier animal en dosis mínimas.",
            activeToxins = listOf("Amatoxinas", "Falotoxinas (resistentes al calor y cocción)"),
            commonSymptoms = listOf("Fase 1 (6-12h): sin síntomas", "Fase 2: cólicos y diarrea coleriforme", "Fase 3: fallo hepático y coma"),
            firstAid = "HOSPITALIZACIÓN URGENTE INMEDIATA. Llevar cualquier resto del hongo para identificación microscópica.",
            safeHandlingTips = "Nunca recolectar setas silvestres con láminas blancas y volva si no se es experto micólogo.",
            commonUses = "Ninguno. Hongo silvestre extremadamente peligroso."
        ),
        ScanResultItem(
            id = "food_chocolate_dark",
            name = "Chocolate Negro / Cacao Puro",
            scientificOrChemicalName = "Theobroma cacao",
            category = ItemCategory.FOOD_MUSHROOM,
            hazardLevel = HazardLevel.HARMFUL,
            toxicityScore = 60,
            isHarmful = true,
            summary = "DELICIOSO Y SEGURO PARA HUMANOS; ALTAMENTE TÓXICO PARA PERROS Y GATOS.",
            humanHazard = "Completamente seguro y saludable para consumo humano responsable.",
            petHazard = "TÓXICO Y POTENCIALMENTE MORTAL PARA PERROS. No metabolizan la teobromina, causando taquicardia, arritmias y convulsiones.",
            activeToxins = listOf("Teobromina", "Cafeína"),
            commonSymptoms = listOf("En mascotas: hiperactividad, jadeo, vómito, convulsiones y arritmias"),
            firstAid = "Para mascotas: si ingirió chocolate puro en las últimas 2 horas, acudir a urgencias veterinarias para inducir el vómito.",
            safeHandlingTips = "Guardar repostería, bombones y cacao en sitios cerrados e inaccesibles para perros.",
            commonUses = "Alimento, repostería y antioxidante para personas."
        ),
        ScanResultItem(
            id = "food_champinon",
            name = "Champiñón de París (Hongo Comestible)",
            scientificOrChemicalName = "Agaricus bisporus",
            category = ItemCategory.FOOD_MUSHROOM,
            hazardLevel = HazardLevel.SAFE,
            toxicityScore = 5,
            isHarmful = false,
            summary = "COMPLETAMENTE SEGURO Y NUTRITIVO. Apto para humanos y sin peligro tóxico.",
            humanHazard = "Inofensivo. Excelente fuente de proteínas, fibra y minerales.",
            petHazard = "Seguro en pequeñas porciones cocidas sin cebolla ni ajo.",
            activeToxins = emptyList(),
            commonSymptoms = emptyList(),
            firstAid = "No requiere. Alimento comestible estándar.",
            safeHandlingTips = "Conservar refrigerado y cocinar antes de consumir.",
            commonUses = "Gastronomía internacional, ensaladas, salteados y sopas."
        ),
        ScanResultItem(
            id = "food_grapes",
            name = "Uvas y Pasas",
            scientificOrChemicalName = "Vitis vinifera",
            category = ItemCategory.FOOD_MUSHROOM,
            hazardLevel = HazardLevel.HARMFUL,
            toxicityScore = 55,
            isHarmful = true,
            summary = "SALUDABLE PARA HUMANOS; VENENOSO PARA PERROS (Fallo Renal Agudo).",
            humanHazard = "Seguro y saludable para personas.",
            petHazard = "Tóxico grave para perros. Incluso 2 o 3 pasas pueden provocar insuficiencia renal aguda fulminante en perros sensibles.",
            activeToxins = listOf("Ácido tartárico y bitartrato de potasio"),
            commonSymptoms = listOf("En perros: letargo, anuria (dejan de orinar), vómito a las pocas horas"),
            firstAid = "Veterinario urgente ante la ingesta de uvas en perros. Requiere fluidoterapia intensiva.",
            safeHandlingTips = "Nunca premiar mascotas con uvas o frutos secos que contengan pasas.",
            commonUses = "Fruta fresca, vino y repostería para humanos."
        ),
        ScanResultItem(
            id = "food_manzana",
            name = "Semillas de Manzana (Pepitas)",
            scientificOrChemicalName = "Malus domestica (pepitas)",
            category = ItemCategory.FOOD_MUSHROOM,
            hazardLevel = HazardLevel.CAUTION,
            toxicityScore = 35,
            isHarmful = true,
            summary = "LA FRUTA ES 100% SEGURA; LAS SEMILLAS CONTIENEN AMIGDALINA (Cianuro).",
            humanHazard = "Comer una manzana entera con sus semillas no suele ser peligroso, pero masticar docenas de semillas trituradas libera cianuro.",
            petHazard = "Evitar que perros coman corazones de manzana con pepitas.",
            activeToxins = listOf("Amigdalina (glucósido cianogénico)"),
            commonSymptoms = listOf("Mareo, dolor de cabeza si se mastican grandes cantidades de pepitas"),
            firstAid = "Desechar los corazones con semillas antes de consumir o dar la pulpa a mascotas.",
            safeHandlingTips = "La pulpa de manzana es deliciosa y segura para personas y perros.",
            commonUses = "Fruta consumida mundialmente."
        ),

        // --- COSMÉTICOS E HIGIENE ---
        ScanResultItem(
            id = "cosm_parabens",
            name = "Parabenos (Propylparaben / Butylparaben)",
            scientificOrChemicalName = "Ésteres de ácido para-hidroxibenzoico",
            category = ItemCategory.COSMETIC,
            hazardLevel = HazardLevel.CAUTION,
            toxicityScore = 42,
            isHarmful = true,
            summary = "DISRUPTOR ENDOCRINO SOSPECHOSO. Conservante químico bajo restricción en cosmética infantil.",
            humanHazard = "Baja toxicidad aguda, pero con sospecha de bioacumulación e interferencia con receptores estrogénicos.",
            petHazard = "Irritación cutánea en animales si se usan champús humanos en su pelaje.",
            activeToxins = listOf("Parabenos de cadena larga"),
            commonSymptoms = listOf("Dermatitis de contacto", "Erupciones cutáneas"),
            firstAid = "Suspender el uso del producto cosmético. Aplicar crema calmante.",
            safeHandlingTips = "Optar por productos certificados 'Paraben-Free' en cosmética diaria y bebés.",
            commonUses = "Conservante antimicrobiano en cremas, lociones y champús."
        ),
        ScanResultItem(
            id = "cosm_formaldehyde",
            name = "Formaldehído / Liberadores de Formaldehído",
            scientificOrChemicalName = "Metanal (CH2O) / DMDM Hidantoína",
            category = ItemCategory.COSMETIC,
            hazardLevel = HazardLevel.HARMFUL,
            toxicityScore = 75,
            isHarmful = true,
            summary = "CARCINÓGENO CLASIFICADO E IRRITANTE SEVERO. Restringido estrictamente en la UE.",
            humanHazard = "Inhalado o absorbido en piel aumenta riesgo de cáncer nasofaríngeo y alergias de contacto crónicas.",
            petHazard = "Tóxico si entra en contacto con mucosas.",
            activeToxins = listOf("Formaldehído libre"),
            commonSymptoms = listOf("Ojos llorosos", "Quemazón en cuero cabelludo", "Dermatitis severa"),
            firstAid = "Lavar de inmediato el cabello o la piel con agua abundante durante 15 minutos.",
            safeHandlingTips = "Evitar tratamientos de alisado de queratina brasileña con vapores fuertes de formol.",
            commonUses = "Tratamientos capilares de alisado antiguo y esmaltes de uñas endurecedores."
        ),
        ScanResultItem(
            id = "cosm_sls",
            name = "Sulfato Laureth de Sodio (SLS / SLES)",
            scientificOrChemicalName = "Sodium Lauryl Sulfate",
            category = ItemCategory.COSMETIC,
            hazardLevel = HazardLevel.CAUTION,
            toxicityScore = 30,
            isHarmful = false,
            summary = "TENSIOACTIVO ESPUMANTE COMÚN. No es cancerígeno, pero puede ser irritante en pieles sensibles.",
            humanHazard = "Seguro en uso enjuagable. Puede causar resequedad o eccema en personas con dermatitis atópica.",
            petHazard = "Reseca excesivamente el estrato córneo de perros y gatos.",
            activeToxins = listOf("Agente deslipidizante / Tensioactivo aniónico"),
            commonSymptoms = listOf("Tirantez en la piel", "Caspa seca", "Prurito"),
            firstAid = "Aclarar bien con agua tibia y aplicar crema hidratante con ceramidas.",
            safeHandlingTips = "Si tienes piel reactiva, busca geles y champús 'Sulfate-Free'.",
            commonUses = "Espumante en pastas dentales, geles de ducha y detergentes."
        ),

        // --- FAUNA E INSECTOS ---
        ScanResultItem(
            id = "critter_procesionaria",
            name = "Oruga Procesionaria del Pino",
            scientificOrChemicalName = "Thaumetopoea pityocampa",
            category = ItemCategory.CRITTER,
            hazardLevel = HazardLevel.CRITICAL,
            toxicityScore = 94,
            isHarmful = true,
            summary = "PELIGRO MORTAL PARA PERROS Y NIÑOS. Sus pelos urticantes desprenden taumetopoína venenosa.",
            humanHazard = "Picor brutal, urticaria, conjuntivitis severa y problemas respiratorios por pelos flotantes.",
            petHazard = "URGENCIA MÁXIMA EN PERROS: Chupar la oruga provoca necrosis de lengua (se cae a trozos) y asfixia en minutos.",
            activeToxins = listOf("Taumetopoína (proteína urticante y necrotizante)"),
            commonSymptoms = listOf("Lengua hinchada y morada", "Hipersalivación aguda", "Urticaria dolorosa"),
            firstAid = "Lavar con agua templada SIN FROTAR (para no romper más pelos). CORRER AL VETERINARIO DE INMEDIATO.",
            safeHandlingTips = "Evitar pinares en primavera (febrero a mayo). No pisar ni acercarse a los bolsones.",
            commonUses = "Especie silvestre forestal perjudicial."
        ),
        ScanResultItem(
            id = "critter_mariquita",
            name = "Mariquita Común (Escarabajo de San Antonio)",
            scientificOrChemicalName = "Coccinella septempunctata",
            category = ItemCategory.CRITTER,
            hazardLevel = HazardLevel.SAFE,
            toxicityScore = 8,
            isHarmful = false,
            summary = "INOFENSIVA Y BENEFICIOSA. Depredadora natural de plagas en plantas.",
            humanHazard = "Totalmente inofensiva para humanos y niños.",
            petHazard = "No tóxica. Si un perro mastica varias puede dejarle sabor amargo transitorio.",
            activeToxins = emptyList(),
            commonSymptoms = emptyList(),
            firstAid = "No requiere.",
            safeHandlingTips = "Proteger en el huerto o jardín: devora pulgones perjudiciales.",
            commonUses = "Control biológico de plagas en agricultura ecológica."
        )
    )

    fun search(query: String, category: ItemCategory? = null): List<ScanResultItem> {
        val q = query.trim().lowercase()
        return items.filter { item ->
            val matchesCategory = category == null || item.category == category
            val matchesQuery = q.isEmpty() ||
                item.name.lowercase().contains(q) ||
                item.scientificOrChemicalName.lowercase().contains(q) ||
                item.summary.lowercase().contains(q) ||
                item.activeToxins.any { it.lowercase().contains(q) } ||
                item.commonUses.lowercase().contains(q)
            matchesCategory && matchesQuery
        }
    }

    fun findById(id: String): ScanResultItem? {
        return items.find { it.id == id }
    }

    fun analyzeIngredientsText(text: String): ScanResultItem {
        val lower = text.lowercase()
        val detectedToxins = mutableListOf<String>()
        var calculatedScore = 15
        var calculatedLevel = HazardLevel.SAFE

        if (lower.contains("cloro") || lower.contains("hipoclorito") || lower.contains("lejia") || lower.contains("lejía")) {
            detectedToxins.add("Hipoclorito de Sodio (Corrosivo)")
            calculatedScore = maxOf(calculatedScore, 90)
        }
        if (lower.contains("hidróxido de sodio") || lower.contains("sosa") || lower.contains("caustica") || lower.contains("cáustica")) {
            detectedToxins.add("Hidróxido de Sodio (Sosa Cáustica - pH extremo)")
            calculatedScore = maxOf(calculatedScore, 98)
        }
        if (lower.contains("formol") || lower.contains("formaldeh") || lower.contains("dmdm")) {
            detectedToxins.add("Formaldehído / Liberador de formol (Carcinógeno clase 1)")
            calculatedScore = maxOf(calculatedScore, 85)
        }
        if (lower.contains("paraben") || lower.contains("parabeno")) {
            detectedToxins.add("Parabenos (Disruptor endocrino bajo sospecha)")
            calculatedScore = maxOf(calculatedScore, 48)
        }
        if (lower.contains("ricin") || lower.contains("adelfa") || lower.contains("oleandr")) {
            detectedToxins.add("Toxina botánica cardíaca / celular crítica")
            calculatedScore = maxOf(calculatedScore, 95)
        }
        if (lower.contains("sulfato") || lower.contains("sulfate") || lower.contains("sls")) {
            detectedToxins.add("Tensioactivo aniónico (Potencial irritante)")
            calculatedScore = maxOf(calculatedScore, 30)
        }
        if (lower.contains("amon") || lower.contains("ammonia")) {
            detectedToxins.add("Amoníaco (Irritante respiratorio fuerte)")
            calculatedScore = maxOf(calculatedScore, 75)
        }

        calculatedLevel = HazardLevel.fromScore(calculatedScore)
        val isHarmful = calculatedScore > 25

        return ScanResultItem(
            id = "analysis_${System.currentTimeMillis()}",
            name = if (detectedToxins.isNotEmpty()) "Análisis de Fórmula / Ingredientes" else "Producto Analizado",
            scientificOrChemicalName = "Composición Química Detectada",
            category = if (lower.contains("planta") || lower.contains("mata") || lower.contains("hoja")) ItemCategory.PLANT else ItemCategory.HOUSEHOLD_CHEMICAL,
            hazardLevel = calculatedLevel,
            toxicityScore = calculatedScore,
            isHarmful = isHarmful,
            summary = if (isHarmful) {
                "Se detectaron componentes con nivel de alerta ${calculatedLevel.shortStatus}. Revisa las toxinas encontradas."
            } else {
                "No se detectaron compuestos de toxicidad crítica conocida en la fórmula introducida."
            },
            humanHazard = if (calculatedScore > 70) {
                "ALERTA: Contiene sustancias con capacidad corrosiva o carcinógena comprobada. Evitar contacto directo con piel y ojos."
            } else if (calculatedScore > 40) {
                "PRECAUCIÓN: Puede provocar irritación cutánea o efectos alérgicos acumulativos en personas sensibles."
            } else {
                "Bajo riesgo para adultos y niños bajo condiciones normales de uso."
            },
            petHazard = if (isHarmful) {
                "Mantener el envase alejado de mascotas. Los residuos en suelo o encimeras pueden transferirse a sus patas."
            } else {
                "Sin peligro evidente para animales domésticos."
            },
            activeToxins = if (detectedToxins.isNotEmpty()) detectedToxins else listOf("Ningún compuesto de alto riesgo identificado"),
            commonSymptoms = if (calculatedScore > 70) listOf("Quemazón", "Irritación ocular", "Dificultad respiratoria") else listOf("Leve sequedad si hay sobreexposición"),
            firstAid = "En caso de contacto con ojos o mucosas, aclarar con agua limpia durante 15 minutos. No ingerir.",
            safeHandlingTips = "Conservar en su envase original con etiqueta legible y tapa cerrada.",
            commonUses = "Inspección de seguridad para ingredientes y etiquetas del hogar."
        )
    }
}
