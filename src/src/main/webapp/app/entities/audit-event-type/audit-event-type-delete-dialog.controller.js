(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Audit_event_typeDeleteController',Audit_event_typeDeleteController);

    Audit_event_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Audit_event_type'];

    function Audit_event_typeDeleteController($uibModalInstance, entity, Audit_event_type) {
        var vm = this;
        vm.audit_event_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Audit_event_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
