(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataenajenantecopscDeleteController',Com_dataenajenantecopscDeleteController);

    Com_dataenajenantecopscDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_dataenajenantecopsc'];

    function Com_dataenajenantecopscDeleteController($uibModalInstance, entity, Com_dataenajenantecopsc) {
        var vm = this;
        vm.com_dataenajenantecopsc = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_dataenajenantecopsc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
