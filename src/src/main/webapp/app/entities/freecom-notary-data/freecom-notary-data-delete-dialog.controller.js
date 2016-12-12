(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_notary_dataDeleteController',Freecom_notary_dataDeleteController);

    Freecom_notary_dataDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_notary_data'];

    function Freecom_notary_dataDeleteController($uibModalInstance, entity, Freecom_notary_data) {
        var vm = this;
        vm.freecom_notary_data = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_notary_data.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
