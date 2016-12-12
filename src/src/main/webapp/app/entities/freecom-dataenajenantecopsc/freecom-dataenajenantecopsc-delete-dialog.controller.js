(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataenajenantecopscDeleteController',Freecom_dataenajenantecopscDeleteController);

    Freecom_dataenajenantecopscDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_dataenajenantecopsc'];

    function Freecom_dataenajenantecopscDeleteController($uibModalInstance, entity, Freecom_dataenajenantecopsc) {
        var vm = this;
        vm.freecom_dataenajenantecopsc = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_dataenajenantecopsc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
