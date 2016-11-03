(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_doneesDetailController', Com_doneesDetailController);

    Com_doneesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_donees', 'Cfdi'];

    function Com_doneesDetailController($scope, $rootScope, $stateParams, entity, Com_donees, Cfdi) {
        var vm = this;
        vm.com_donees = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_doneesUpdate', function(event, result) {
            vm.com_donees = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
