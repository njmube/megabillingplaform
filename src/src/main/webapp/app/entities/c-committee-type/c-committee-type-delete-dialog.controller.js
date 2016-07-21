(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_committee_typeDeleteController',C_committee_typeDeleteController);

    C_committee_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_committee_type'];

    function C_committee_typeDeleteController($uibModalInstance, entity, C_committee_type) {
        var vm = this;
        vm.c_committee_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_committee_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
