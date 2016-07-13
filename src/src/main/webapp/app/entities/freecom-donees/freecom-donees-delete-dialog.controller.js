(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_doneesDeleteController',Freecom_doneesDeleteController);

    Freecom_doneesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_donees'];

    function Freecom_doneesDeleteController($uibModalInstance, entity, Freecom_donees) {
        var vm = this;
        vm.freecom_donees = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_donees.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
