(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-emitter', {
            parent: 'entity',
            url: '/free-emitter?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_emitter.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-emitter/free-emitters.html',
                    controller: 'Free_emitterController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_emitter');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
		.state('free-emitter.new', {
			parent: 'free-emitter',
			url: '/new',
			data: {
				authorities: ['ROLE_USER'],
				pageTitle: 'megabillingplatformApp.free_emitter.home.createLabel'
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/free-emitter/free-emitter-new.html',
					controller: 'Free_emitterNewController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				entity: ['Free_emitter', function(Free_emitter) {
                            return Free_emitter.get({id : 0});
                        }],
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('free_emitter');
					$translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('home');
					return $translate.refresh();
				}]
			}
		})
        .state('free-emitter.newAdmin', {
                parent: 'free-emitter',
                url: '/newAdmin',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'megabillingplatformApp.free_emitter.home.createLabel'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/free-emitter/free-emitter-newAdmin.html',
                        controller: 'Free_emitterNewAdminController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['Free_emitter', function(Free_emitter) {
                        return Free_emitter.get({id : 0});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('free_emitter');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('home');
                        return $translate.refresh();
                    }]
                }
            })
        .state('passCertificate', {
            parent: 'free-emitter.new',
            url: '/passCertificate',
            data: {
                authorities: ['ROLE_USER']
            },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/free-emitter/free-emitter-new.html',
                        controller: 'Free_emitterNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['Free_emitter', function(Free_emitter) {
                        return Free_emitter.get({id : 0});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('free_emitter');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
        })
        .state('free-emitter.edit', {
            parent: 'free-emitter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-emitter/free-emitter-dialog.html',
                    controller: 'Free_emitterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_emitter', function(Free_emitter) {
                            return Free_emitter.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-emitter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-emitter.delete', {
            parent: 'free-emitter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-emitter/free-emitter-delete-dialog.html',
                    controller: 'Free_emitterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_emitter', function(Free_emitter) {
                            return Free_emitter.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-emitter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
