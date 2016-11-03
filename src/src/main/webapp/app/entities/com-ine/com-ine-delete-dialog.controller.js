(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ineDeleteController',Com_ineDeleteController);

    Com_ineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_ine'];

    function Com_ineDeleteController($uibModalInstance, entity, Com_ine) {
        var vm = this;
        vm.com_ine = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_ine.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
