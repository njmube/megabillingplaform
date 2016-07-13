(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_pficDeleteController',Freecom_pficDeleteController);

    Freecom_pficDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_pfic'];

    function Freecom_pficDeleteController($uibModalInstance, entity, Freecom_pfic) {
        var vm = this;
        vm.freecom_pfic = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_pfic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
