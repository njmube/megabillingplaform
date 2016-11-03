(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_commodityDeleteController',Com_commodityDeleteController);

    Com_commodityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_commodity'];

    function Com_commodityDeleteController($uibModalInstance, entity, Com_commodity) {
        var vm = this;
        vm.com_commodity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_commodity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
