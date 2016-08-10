(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_beneficiaryDetailController', Freecom_beneficiaryDetailController);

    Freecom_beneficiaryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_beneficiary'];

    function Freecom_beneficiaryDetailController($scope, $rootScope, $stateParams, entity, Freecom_beneficiary) {
        var vm = this;

        vm.freecom_beneficiary = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_beneficiaryUpdate', function(event, result) {
            vm.freecom_beneficiary = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
