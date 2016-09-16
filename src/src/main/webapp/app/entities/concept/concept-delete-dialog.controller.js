(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDeleteController',ConceptDeleteController);

    ConceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Concept'];

    function ConceptDeleteController($uibModalInstance, entity, Concept) {
        var vm = this;

        vm.concept = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
