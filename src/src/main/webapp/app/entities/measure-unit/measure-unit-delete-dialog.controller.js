(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Measure_unitDeleteController',Measure_unitDeleteController);

    Measure_unitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Measure_unit'];

    function Measure_unitDeleteController($uibModalInstance, entity, Measure_unit) {
        var vm = this;
        vm.measure_unit = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Measure_unit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
