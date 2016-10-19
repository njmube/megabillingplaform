(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unit_conceptDeleteController',Measure_unit_conceptDeleteController);

    Measure_unit_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Measure_unit_concept'];

    function Measure_unit_conceptDeleteController($uibModalInstance, entity, Measure_unit_concept) {
        var vm = this;
        vm.measure_unit_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Measure_unit_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
