(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_beneficiaryDetailController', Com_beneficiaryDetailController);

    Com_beneficiaryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_beneficiary'];

    function Com_beneficiaryDetailController($scope, $rootScope, $stateParams, entity, Com_beneficiary) {
        var vm = this;
        vm.com_beneficiary = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_beneficiaryUpdate', function(event, result) {
            vm.com_beneficiary = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
