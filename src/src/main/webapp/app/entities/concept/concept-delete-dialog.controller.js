(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDeleteController',ConceptDeleteController);

    ConceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Part_concept', 'Customs_info', 'Concept'];

    function ConceptDeleteController($uibModalInstance, entity, Part_concept, Customs_info, Concept) {
        var vm = this;

        vm.concept = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        vm.part_concepts = Part_concept.query({conceptid: vm.concept.id});
        vm.customs_infos = Customs_info.query({conceptid: vm.concept.id});

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            var i;
            for(i = 0; i < vm.part_concepts.length; i++){
                var part_concept = vm.part_concepts[i];
                Part_concept.delete({id: part_concept.id});
            }

            var j;
            for(j = 0; j < vm.customs_infos.length; j++) {
                var customs_info = vm.customs_infos[j];
                Customs_info.delete({id: customs_info.id});
            }

            Concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
