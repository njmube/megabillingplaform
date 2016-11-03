(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_speiDetailController', Com_speiDetailController);

    Com_speiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_spei', 'Cfdi'];

    function Com_speiDetailController($scope, $rootScope, $stateParams, entity, Com_spei, Cfdi) {
        var vm = this;
        vm.com_spei = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_speiUpdate', function(event, result) {
            vm.com_spei = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
