(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_commodityDeleteController',Freecom_commodityDeleteController);

    Freecom_commodityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_commodity'];

    function Freecom_commodityDeleteController($uibModalInstance, entity, Freecom_commodity) {
        var vm = this;
        vm.freecom_commodity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_commodity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
