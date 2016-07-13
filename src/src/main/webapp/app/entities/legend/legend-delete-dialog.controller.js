(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('LegendDeleteController',LegendDeleteController);

    LegendDeleteController.$inject = ['$uibModalInstance', 'entity', 'Legend'];

    function LegendDeleteController($uibModalInstance, entity, Legend) {
        var vm = this;
        vm.legend = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Legend.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
