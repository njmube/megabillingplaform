(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_spei_thirdDetailController', Com_spei_thirdDetailController);

    Com_spei_thirdDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_spei_third', 'Com_spei', 'Com_payer', 'Com_beneficiary'];

    function Com_spei_thirdDetailController($scope, $rootScope, $stateParams, entity, Com_spei_third, Com_spei, Com_payer, Com_beneficiary) {
        var vm = this;
        vm.com_spei_third = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_spei_thirdUpdate', function(event, result) {
            vm.com_spei_third = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
