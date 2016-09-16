(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Part_conceptDeleteController',Part_conceptDeleteController);

    Part_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Part_concept'];

    function Part_conceptDeleteController($uibModalInstance, entity, Part_concept) {
        var vm = this;

        vm.part_concept = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Part_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
